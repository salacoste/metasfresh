-- 2019-01-15T18:04:02.516
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsOneInstanceOnly,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth) VALUES (0,572603,0,540556,TO_TIMESTAMP('2019-01-15 18:04:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','N','N','N','N','Y','Handling Unit Assignment','N',TO_TIMESTAMP('2019-01-15 18:04:02','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0)
;

-- 2019-01-15T18:04:02.526
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Window_ID=540556 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2019-01-15T18:04:02.617
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_window_translation_from_ad_element(572603) 
;

-- 2019-01-15T18:04:10.305
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET InternalName='M_HU_Assignment',Updated=TO_TIMESTAMP('2019-01-15 18:04:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540556
;

-- 2019-01-15T18:05:12.659
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,HasTree,ImportFields,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,572603,0,541403,540569,540556,TO_TIMESTAMP('2019-01-15 18:05:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','N','N','Y','N','Y','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'Handling Unit Assignment','N',10,0,TO_TIMESTAMP('2019-01-15 18:05:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-15T18:05:12.659
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=541403 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2019-01-15T18:05:12.669
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(572603) 
;

-- 2019-01-15T18:06:50.415
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,550370,570871,0,541403,TO_TIMESTAMP('2019-01-15 18:06:50','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.handlingunits','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','N','N','Mandant',TO_TIMESTAMP('2019-01-15 18:06:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-15T18:06:50.415
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=570871 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-01-15T18:06:50.487
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,550371,570872,0,541403,TO_TIMESTAMP('2019-01-15 18:06:50','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.handlingunits','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2019-01-15 18:06:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-15T18:06:50.487
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=570872 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-01-15T18:06:50.557
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,550374,570873,0,541403,TO_TIMESTAMP('2019-01-15 18:06:50','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.handlingunits','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2019-01-15 18:06:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-15T18:06:50.557
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=570873 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-01-15T18:06:50.627
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,550377,570874,0,541403,TO_TIMESTAMP('2019-01-15 18:06:50','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','M_HU_Assignment',TO_TIMESTAMP('2019-01-15 18:06:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-15T18:06:50.627
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=570874 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-01-15T18:06:50.700
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,550378,570875,0,541403,TO_TIMESTAMP('2019-01-15 18:06:50','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Handling Units',TO_TIMESTAMP('2019-01-15 18:06:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-15T18:06:50.700
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=570875 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-01-15T18:06:50.770
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,550379,570876,0,541403,TO_TIMESTAMP('2019-01-15 18:06:50','YYYY-MM-DD HH24:MI:SS'),100,'Database Table information',10,'de.metas.handlingunits','The Database Table provides the information of the table definition','Y','N','N','N','N','N','N','N','DB-Tabelle',TO_TIMESTAMP('2019-01-15 18:06:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-15T18:06:50.770
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=570876 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-01-15T18:06:50.840
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,550380,570877,0,541403,TO_TIMESTAMP('2019-01-15 18:06:50','YYYY-MM-DD HH24:MI:SS'),100,'Direct internal record ID',10,'de.metas.handlingunits','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','N','N','N','N','N','N','N','Datensatz-ID',TO_TIMESTAMP('2019-01-15 18:06:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-15T18:06:50.840
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=570877 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-01-15T18:06:50.910
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,550425,570878,0,541403,TO_TIMESTAMP('2019-01-15 18:06:50','YYYY-MM-DD HH24:MI:SS'),100,'Menge',14,'de.metas.handlingunits','Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','N','N','N','N','N','N','N','Menge',TO_TIMESTAMP('2019-01-15 18:06:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-15T18:06:50.910
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=570878 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-01-15T18:06:50.980
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551435,570879,0,541403,TO_TIMESTAMP('2019-01-15 18:06:50','YYYY-MM-DD HH24:MI:SS'),100,'Shall we transfer packing materials along with the HU',1,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Transfer Packing Materials',TO_TIMESTAMP('2019-01-15 18:06:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-15T18:06:50.980
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=570879 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-01-15T18:06:51.040
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551483,570880,0,541403,TO_TIMESTAMP('2019-01-15 18:06:50','YYYY-MM-DD HH24:MI:SS'),100,'Handling Unit (Loading Unit)',10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Handling Unit (LU)',TO_TIMESTAMP('2019-01-15 18:06:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-15T18:06:51.050
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=570880 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-01-15T18:06:51.120
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551484,570881,0,541403,TO_TIMESTAMP('2019-01-15 18:06:51','YYYY-MM-DD HH24:MI:SS'),100,'Handling Unit of type Tranding Unit',10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Handling Unit (TU)',TO_TIMESTAMP('2019-01-15 18:06:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-15T18:06:51.120
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=570881 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-01-15T18:06:51.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551756,570882,0,541403,TO_TIMESTAMP('2019-01-15 18:06:51','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','CU Handling Unit (VHU)',TO_TIMESTAMP('2019-01-15 18:06:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-15T18:06:51.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=570882 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-01-15T18:06:51.260
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,552291,570883,0,541403,TO_TIMESTAMP('2019-01-15 18:06:51','YYYY-MM-DD HH24:MI:SS'),100,250,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Produkte',TO_TIMESTAMP('2019-01-15 18:06:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-15T18:06:51.260
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=570883 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-01-15T18:07:28.415
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541403,540954,TO_TIMESTAMP('2019-01-15 18:07:28','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-01-15 18:07:28','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2019-01-15T18:07:28.425
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=540954 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-01-15T18:07:28.515
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541241,540954,TO_TIMESTAMP('2019-01-15 18:07:28','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-01-15 18:07:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-15T18:07:28.605
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541242,540954,TO_TIMESTAMP('2019-01-15 18:07:28','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2019-01-15 18:07:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-15T18:07:28.697
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,541241,541955,TO_TIMESTAMP('2019-01-15 18:07:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2019-01-15 18:07:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-15T18:11:36.505
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,570876,0,541403,554531,541955,'F',TO_TIMESTAMP('2019-01-15 18:11:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'DB-Tabelle',10,0,0,TO_TIMESTAMP('2019-01-15 18:11:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-15T18:11:58.150
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,570877,0,541403,554532,541955,'F',TO_TIMESTAMP('2019-01-15 18:11:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Datensatz-ID',20,0,0,TO_TIMESTAMP('2019-01-15 18:11:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-15T18:12:12.482
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,570875,0,541403,554533,541955,'F',TO_TIMESTAMP('2019-01-15 18:12:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Handling Units',30,0,0,TO_TIMESTAMP('2019-01-15 18:12:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-15T18:12:30.453
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,570880,0,541403,554534,541955,'F',TO_TIMESTAMP('2019-01-15 18:12:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Handling Unit (LU)',40,0,0,TO_TIMESTAMP('2019-01-15 18:12:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-15T18:12:44.202
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,570881,0,541403,554535,541955,'F',TO_TIMESTAMP('2019-01-15 18:12:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Handling Unit (TU)',50,0,0,TO_TIMESTAMP('2019-01-15 18:12:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-15T18:13:01.118
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,570883,0,541403,554536,541955,'F',TO_TIMESTAMP('2019-01-15 18:13:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Produkte',60,0,0,TO_TIMESTAMP('2019-01-15 18:13:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-15T18:13:16.113
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,570879,0,541403,554537,541955,'F',TO_TIMESTAMP('2019-01-15 18:13:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Transfer Packing Materials',70,0,0,TO_TIMESTAMP('2019-01-15 18:13:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-15T18:13:27.108
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,570872,0,541403,554538,541955,'F',TO_TIMESTAMP('2019-01-15 18:13:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Sektion',80,0,0,TO_TIMESTAMP('2019-01-15 18:13:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-15T18:13:40.266
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,570871,0,541403,554539,541955,'F',TO_TIMESTAMP('2019-01-15 18:13:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Mandant',90,0,0,TO_TIMESTAMP('2019-01-15 18:13:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-15T18:14:50.985
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541242,541956,TO_TIMESTAMP('2019-01-15 18:14:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2019-01-15 18:14:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-15T18:15:26.309
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541956, SeqNo=10,Updated=TO_TIMESTAMP('2019-01-15 18:15:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554537
;

-- 2019-01-15T18:15:38.369
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541242,541957,TO_TIMESTAMP('2019-01-15 18:15:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2019-01-15 18:15:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-15T18:16:10.308
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541957, SeqNo=10,Updated=TO_TIMESTAMP('2019-01-15 18:16:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554538
;

-- 2019-01-15T18:16:25.288
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541957, SeqNo=20,Updated=TO_TIMESTAMP('2019-01-15 18:16:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554539
;

-- 2019-01-15T18:17:23.806
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2019-01-15 18:17:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=541957
;

-- 2019-01-15T18:17:34.313
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541242,541958,TO_TIMESTAMP('2019-01-15 18:17:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','products',20,TO_TIMESTAMP('2019-01-15 18:17:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-15T18:17:47.328
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541958, SeqNo=10,Updated=TO_TIMESTAMP('2019-01-15 18:17:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554536
;

-- 2019-01-15T18:19:05.356
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,572603,541172,0,540556,TO_TIMESTAMP('2019-01-15 18:19:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','M_HU_Assignment','Y','N','N','N','N','Handling Unit Assignment',TO_TIMESTAMP('2019-01-15 18:19:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-15T18:19:05.356
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=541172 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2019-01-15T18:19:05.366
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541172, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541172)
;

-- 2019-01-15T18:19:05.376
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_menu_translation_from_ad_element(572603) 
;

-- 2019-01-15T18:19:05.470
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540796 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:05.470
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540798 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:05.470
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540797 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:05.470
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540829 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:05.470
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540973 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:05.470
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540830 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:05.470
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540831 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:05.470
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540844 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:05.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540846 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:05.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540977 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:05.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540900 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:05.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540856 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:05.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540857 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:05.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540860 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:05.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540866 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:05.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541057 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:05.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540918 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:05.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541011 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:05.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541083 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:05.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000057 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:05.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000065 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:05.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000075 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:05.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541117 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:05.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541172 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:12.669
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540796 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:12.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540798 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:12.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540797 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:12.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540829 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:12.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540973 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:12.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540830 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:12.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540831 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:12.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540844 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:12.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540846 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:12.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540977 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:12.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541172 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:12.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540900 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:12.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540856 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:12.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540857 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:12.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540860 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:12.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540866 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:12.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541057 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:12.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540918 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:12.689
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541011 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:12.689
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541083 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:12.689
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000057 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:12.689
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000065 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:12.689
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000075 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:12.689
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541117 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:19.417
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540796 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:19.430
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540798 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:19.432
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540797 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:19.433
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540829 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:19.433
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540973 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:19.434
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540830 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:19.435
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540831 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:19.436
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540844 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:19.437
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540846 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:19.438
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540977 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:19.438
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540900 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:19.439
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540856 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:19.440
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541172 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:19.440
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540857 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:19.441
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540860 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:19.441
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540866 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:19.442
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541057 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:19.443
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540918 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:19.443
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541011 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:19.444
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541083 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:19.445
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000057 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:19.445
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000065 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:19.446
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000075 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:19.447
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541117 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:22.597
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540796 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:22.598
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540798 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:22.598
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540797 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:22.599
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540829 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:22.599
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540973 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:22.600
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540830 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:22.600
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540831 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:22.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540844 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:22.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540846 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:22.602
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540977 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:22.603
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540900 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:22.603
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541172 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:22.604
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540856 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:22.605
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540857 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:22.605
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540860 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:22.606
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540866 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:22.607
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541057 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:22.607
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540918 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:22.608
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541011 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:22.609
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541083 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:22.609
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000057 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:22.610
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000065 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:22.610
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000075 AND AD_Tree_ID=10
;

-- 2019-01-15T18:19:22.610
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541117 AND AD_Tree_ID=10
;

-- 2019-01-15T18:24:40.697
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2019-01-15 18:24:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554531
;

-- 2019-01-15T18:24:40.704
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2019-01-15 18:24:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554532
;

-- 2019-01-15T18:24:40.706
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2019-01-15 18:24:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554533
;

-- 2019-01-15T18:24:40.709
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2019-01-15 18:24:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554534
;

-- 2019-01-15T18:24:40.711
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2019-01-15 18:24:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554535
;

-- 2019-01-15T18:24:40.713
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2019-01-15 18:24:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554536
;

-- 2019-01-15T18:24:40.715
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2019-01-15 18:24:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554537
;

-- 2019-01-15T18:24:40.717
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2019-01-15 18:24:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554538
;

