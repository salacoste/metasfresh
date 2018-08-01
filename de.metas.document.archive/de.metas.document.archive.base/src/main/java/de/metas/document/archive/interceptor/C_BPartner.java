package de.metas.document.archive.interceptor;

import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.ModelValidator;

import de.metas.adempiere.model.I_AD_User;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.document.archive.api.IBPartnerBL;
import de.metas.document.archive.api.IDocOutboundDAO;
import de.metas.document.archive.model.I_C_BPartner;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;

/**
 * sync flags
 *
 * @author cg
 *
 */
@Validator(I_C_BPartner.class)
class C_BPartner
{

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = I_C_Doc_Outbound_Log.COLUMNNAME_IsInvoiceEmailEnabled)
	public void updateFlag(final I_C_BPartner bpartner)
	{
		final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
		final IBPartnerBL bPartnerBL = Services.get(IBPartnerBL.class);
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

		final I_AD_User defaultContact = bPartnerDAO.retrieveDefaultContactOrNull(bpartner, I_AD_User.class);
		final de.metas.document.archive.model.I_AD_User user = create(defaultContact, de.metas.document.archive.model.I_AD_User.class);

		final boolean isInvoiceEmailEnabled = bPartnerBL.isInvoiceEmailEnabled(bpartner, user);

		final int invoiceTableId = adTableDAO.retrieveTableId(I_C_Invoice.Table_Name);
		//
		// retrieve latest log
		final I_C_Doc_Outbound_Log docExchange = Services.get(IDocOutboundDAO.class).retrieveLog(
				PlainContextAware.newOutOfTrx(),
				bpartner.getC_BPartner_ID(),
				invoiceTableId);
		if (docExchange == null)
		{
			return;
		}

		// update outbound log accordingly which will trigger a validator <code>C_Doc_Outbound_Log</code> which will create the notification
		// update only for invoices
		docExchange.setIsInvoiceEmailEnabled(isInvoiceEmailEnabled);
		save(docExchange);
	}

}
