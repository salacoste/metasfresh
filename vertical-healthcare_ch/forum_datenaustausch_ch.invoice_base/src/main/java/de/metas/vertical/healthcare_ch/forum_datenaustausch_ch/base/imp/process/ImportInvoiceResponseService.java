package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.imp.process;

/*
 * #%L
 * metasfresh-pharma
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.BPartnerQuery;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.invoice_gateway.spi.model.imp.ImportedInvoiceResponse;
import de.metas.logging.LogManager;
import de.metas.notification.INotificationBL;
import de.metas.notification.Recipient;
import de.metas.notification.UserNotificationRequest;
import de.metas.notification.UserNotificationRequest.TargetRecordAction;
import de.metas.process.PInstanceId;
import de.metas.user.User;
import de.metas.user.UserId;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.imp.InvoiceRejectionDetailId;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Invoice_Rejection_Detail;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// package visibility
@Service
class ImportInvoiceResponseService
{
	private final static transient Logger log = LogManager.getLogger(ImportInvoiceResponseService.class);

	private static final String MSG_NOT_ALL_FILES_IMPORTED_NOTIFICATION = "de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.imp.process.C_Invoice_ImportInvoiceResponse.NotAllFilesImportedNotification";

	private static final String MSG_INVOICE_REJECTED_NOTIFICATION_SUBJECT = "de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.imp.process.C_Invoice_ImportInvoiceResponse.InvoiceRejectedNotification_Subject";
	private static final String MSG_INVOICE_REJECTED_NOTIFICATION_CONTENT_WHEN_USER_EXISTS = "de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.imp.process.C_Invoice_ImportInvoiceResponse.InvoiceRejectedNotification_Content_WhenUserExists";
	private static final String MSG_INVOICE_REJECTED_NOTIFICATION_CONTENT_WHEN_USER_DOES_NOT_EXIST = "de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.imp.process.C_Invoice_ImportInvoiceResponse.InvoiceRejectedNotification_Content_WhenUserDoesNotExist";

	private static final int WINDOW_ID_AD_PInstance_ID = 332; // FIXME Hardcoded

	private static final Topic INVOICE_EVENTBUS_TOPIC = Topic.builder()
			.name("de.metas.invoicecandidate.UserNotifications")
			.type(Type.REMOTE)
			.build();

	private final INotificationBL notificationBL = Services.get(INotificationBL.class);

	private final IBPartnerDAO ibPartnerDAO = Services.get(IBPartnerDAO.class);

	private final UserRepository userRepository;

	ImportInvoiceResponseService(final UserRepository userRepository)
	{
		this.userRepository = userRepository;
	}

	// package visibility
	// i believe these 2 methods should be merged into 1 since all that's different is a single TRL.
	void sendNotificationDefaultUserDoesNotExist(@NonNull final ImportedInvoiceResponse responseWithTags, final InvoiceRejectionDetailId invoiceRejectionDetailId, final UserId userId)
	{
		final Recipient recipient = Recipient.user(userId);

		final TableRecordReference invoiceRef = TableRecordReference.of(I_C_Invoice_Rejection_Detail.Table_Name, invoiceRejectionDetailId);

		final UserNotificationRequest userNotificationRequest = UserNotificationRequest
				.builder()
				.topic(INVOICE_EVENTBUS_TOPIC)
				.recipient(recipient)
				.subjectADMessage(MSG_INVOICE_REJECTED_NOTIFICATION_SUBJECT)
				.subjectADMessageParam(responseWithTags.getDocumentNumber())
				.contentADMessage(MSG_INVOICE_REJECTED_NOTIFICATION_CONTENT_WHEN_USER_DOES_NOT_EXIST)
				.contentADMessageParam(invoiceRef)
				.targetAction(TargetRecordAction.of(invoiceRef))
				.build();

		notificationBL.send(userNotificationRequest);
		log.info("Send notification to recipient={}", recipient);
	}

	// package visibility
	void sendNotificationDefaultUserExists(@NonNull final ImportedInvoiceResponse responseWithTags, final InvoiceRejectionDetailId invoiceRejectionDetailId, final UserId userId)
	{
		final Recipient recipient = Recipient.user(userId);
		final User user = userRepository.getByIdInTrx(userId);

		final TableRecordReference invoiceRef = TableRecordReference.of(I_C_Invoice_Rejection_Detail.Table_Name, invoiceRejectionDetailId);

		final UserNotificationRequest userNotificationRequest = UserNotificationRequest
				.builder()
				.topic(INVOICE_EVENTBUS_TOPIC)
				.recipient(recipient)
				.subjectADMessage(MSG_INVOICE_REJECTED_NOTIFICATION_SUBJECT)
				.subjectADMessageParam(responseWithTags.getDocumentNumber())
				.contentADMessage(MSG_INVOICE_REJECTED_NOTIFICATION_CONTENT_WHEN_USER_EXISTS)
				.contentADMessageParam(user.getName())
				.contentADMessageParam(invoiceRef)
				.targetAction(TargetRecordAction.of(invoiceRef))
				.build();

		notificationBL.send(userNotificationRequest);
		log.info("Send notification to recipient={}", recipient);
	}

	// package visibility
	Optional<UserId> retrieveOrgDefaultContactByGLN(final String gln)
	{
		final List<Integer> bpartnerIds = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BPartner_Location.class)
				.addEqualsFilter(I_C_BPartner_Location.COLUMN_GLN, gln)
				.orderBy(I_C_BPartner.COLUMNNAME_C_BPartner_ID) // just to have an predictable order
				.create()
				.listDistinct(I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID, Integer.class);

		return bpartnerIds.stream()
				.map(BPartnerId::ofRepoId)
				.map(ibPartnerDAO::getDefaultContactId)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.findFirst();
	}

	// package visibility
	int retrieveOrgByGLN(final String gln)
	{
		final BPartnerQuery query = BPartnerQuery.builder()
				.locationGln(gln)
				.build();
		final Optional<BPartnerId> partnerIdOptional = ibPartnerDAO.retrieveBPartnerIdBy(query);
		int billerOrg = 0;
		if (partnerIdOptional.isPresent())
		{
			ibPartnerDAO.getById(partnerIdOptional.get()).getAD_OrgBP_ID();
			billerOrg = ibPartnerDAO.getById(partnerIdOptional.get()).getAD_Org_ID();
		}
		return billerOrg;
	}

	// package visibility
	void sendNotificationNotAllFilesImported(final UserId userId, final PInstanceId pinstanceId, final int repoId)
	{
		final UserNotificationRequest userNotificationRequest = UserNotificationRequest
				.builder()
				.recipient(Recipient.user(userId))
				.contentADMessage(MSG_NOT_ALL_FILES_IMPORTED_NOTIFICATION)
				.contentADMessageParam(repoId)
				.targetAction(TargetRecordAction.ofRecordAndWindow(TableRecordReference.of(I_AD_PInstance.Table_Name, pinstanceId), WINDOW_ID_AD_PInstance_ID))
				.build();
		notificationBL.send(userNotificationRequest);
	}
}
