-- 2017-11-29T08:58:13.571
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1086,0,186,540499,549427,'F',TO_TIMESTAMP('2017-11-29 08:58:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','Y','N','N','Beschreibung',200,0,0,TO_TIMESTAMP('2017-11-29 08:58:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T08:58:44.200
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=6,Updated=TO_TIMESTAMP('2017-11-29 08:58:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549427
;


-- 2018-01-24T22:17:26.386
-- URL zum Konzept
UPDATE AD_SysConfig SET Value='description fields',Updated=TO_TIMESTAMP('2018-01-24 22:17:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=50003
;

-- 2018-01-24T22:20:26.384
-- description fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,501661,561516,0,186,0,TO_TIMESTAMP('2018-01-24 22:20:26','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','N','N','N','N','N','Schlusstext',620,600,0,1,1,TO_TIMESTAMP('2018-01-24 22:20:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-24T22:20:26.387
-- description fields
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=561516 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-01-24T22:20:26.407
-- description fields
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(501680,NULL) 
;

-- 2018-01-24T22:20:41.899
-- description fields
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,561516,0,186,540499,550424,'F',TO_TIMESTAMP('2018-01-24 22:20:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','Y','N','N','Schlusstext',7,0,0,TO_TIMESTAMP('2018-01-24 22:20:41','YYYY-MM-DD HH24:MI:SS'),100)
;

