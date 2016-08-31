create view v_data  --oracle+mysql
 
As  

 SELECT * from (
	 select top 1 STCD as ZBM,isnull(UPZ,0) as SSW,isnull(DWZ,0) as XSW,isnull(TGTQ,0) as LN,TM as TM  --三河闸 最新数据
	From yzsq_was_r	 
	Where STCD='51002650' order by TM desc
 )t1
 
 union
 
 SELECT  * from (
	 select top 1 STCD as ZBM,isnull(Z,0) as SSW,null as XSW,isnull(Q,0) as LN,TM as TM --蒋坝 最新数据
	From yzsq_RIVER_R	 
	Where STCD='50916500' order by TM desc
 )t2

 union
  SELECT  * from (
	 select top 1 STCD as ZBM,isnull(Z,0) as SSW,null as XSW,isnull(Q,0) as LN,TM as TM  --高邮湖 最新数据
	From yzsq_RIVER_R	 
	Where STCD='51003650' order by TM desc
 )t3

 union
   SELECT * from (
	 select top 1 STCD as ZBM,isnull(Z,0) as SSW,null as XSW,isnull(Q,0) as LN,TM as TM --六闸（三） 最新数据
	From yzsq_RIVER_R	 
	Where STCD='51003800' order by TM desc
 )t4
 
  union
  SELECT * from (
	 select top 1 STCD as ZBM,isnull(TDZ,0) as SSW ,null as XSW,null as LN,TM as TM  --三江营 最新数据
	From yzsq_TIDE_R	 
	Where STCD='51004150' order by TM desc
 )t5
  union
  SELECT * from (
	select top 1 STCD as ZBM,isnull(UPZ,0) as SSW ,isnull(DWZ,0) as XSW,isnull(TGTQ,0) as LN,TM as TM --瓜洲闸 最新数据
	From yzsq_WAS_R
	Where STCD='51005400' order by TM desc
 )t6
union
  SELECT * from (
	select top 1 STCD as ZBM,isnull(UPZ,0) as SSW ,isnull(DWZ,0) as XSW,isnull(TGTQ,0) as LN,TM as TM --泗源沟闸 最新数据
	From yzsq_WAS_R
	Where STCD='62705551' order by TM desc
 )t7
 union
  SELECT * from (
	 select top 1 STCD as ZBM,isnull(Z,0) as SSW,null as XSW,isnull(Q,0) as LN,TM as TM --大通 最新数据
	From yzsq_RIVER_R	 
	Where STCD='60115000' order by TM desc
 )t8
  union
  SELECT * from (
	 select top 1  STCD as ZBM,isnull(Z,0) as SSW,null as XSW,isnull(Q,0) as LN,TM as TM --陆庄 最新数据
	From yzsq_RIVER_R	 
	Where STCD='51008600' order by TM desc
 )t9
  union
  SELECT  * from (
	 select top 1 STCD as ZBM,isnull(Z,0) as SSW,null as XSW,isnull(Q,0) as LN,TM as TM --兴华 最新数据
	From yzsq_RIVER_R	 
	Where STCD='51007350' order by TM desc
 )t10
  union
  SELECT * from (
	 select top 1 STCD as ZBM,isnull(Z,0) as SSW,null as XSW,isnull(Q,0) as LN,TM as TM --射阳镇 最新数据
	From yzsq_RIVER_R	 
	Where STCD='51008700' order by TM desc
 )t11
 union
  SELECT * from (
	 select top 1 STCD as ZBM,isnull(Z,0) as SSW,null as XSW,isnull(Q,0) as LN,TM as TM --三垛 最新数据
	From yzsq_RIVER_R	 
	Where STCD='51006950' order by TM desc
 )t12
 union
  SELECT * from (
	 select top 1 STCD as ZBM,isnull(Z,0) as SSW,null as XSW,isnull(Q,0) as LN,TM as TM --宝应 最新数据
	From yzsq_RIVER_R	 
	Where STCD='51005000' order by TM desc
 )t13
 union
  SELECT * from (
	 select top 1 STCD as ZBM,isnull(Z,0) as SSW,null as XSW,isnull(Q,0) as LN,TM as TM --高邮（大） 最新数据
	From yzsq_RIVER_R	 
	Where STCD='51005200' order by TM desc
 )t14
  union
  SELECT * from (
	 select top 1 STCD as ZBM,isnull(Z,0) as SSW,null as XSW,isnull(Q,0) as LN,TM as TM --邵伯（大） 最新数据
	From yzsq_RIVER_R	 
	Where STCD='51005250' order by TM desc
 )t15
 union
  SELECT * from (
	 select top 1 STCD as ZBM,isnull(PPUPZ,0) as SSW,isnull(PPDWZ,0) as XSW,isnull(PMPQ,0) as LN,TM as TM  --江都一站 最新数据
	From yzsq_PUMP_R	 
	Where STCD='51005652' order by TM desc
 )t16
 union
  SELECT * from (
	 select top 1 STCD as ZBM,isnull(PPUPZ,0) as SSW,isnull(PPDWZ,0) as XSW,isnull(PMPQ,0) as LN,TM as TM  --江都二站 最新数据
	From yzsq_PUMP_R	 
	Where STCD='51005653' order by TM desc
 )t17
 union
  SELECT * from (
	 select top 1 STCD as ZBM,isnull(PPUPZ,0) as SSW,isnull(PPDWZ,0) as XSW,isnull(PMPQ,0) as LN,TM as TM  --江都三站 最新数据
	From yzsq_PUMP_R	 
	Where STCD='51005654' order by TM desc
 )t18
 union
  SELECT * from (
	 select top 1 STCD as ZBM,isnull(PPUPZ,0) as SSW,isnull(PPDWZ,0) as XSW,isnull(PMPQ,0) as LN,TM as TM  --江都四站 最新数据
	From yzsq_PUMP_R	 
	Where STCD='51005655' order by TM desc
 )t19
 union
  SELECT * from (
	 select top 1 STCD as ZBM,isnull(UPZ,0) as SSW,isnull(DWZ,0) as XSW,isnull(TGTQ,0) as LN,TM as TM--江都东闸 最新数据
	From yzsq_WAS_R	 
	Where STCD='51005750' order by TM desc
 )t20
 union
  SELECT * from (
	select top 1 STCD as ZBM,isnull(RZ,0) as SSW,null as XSW,isnull(INQ,0) as LN,TM as TM--月塘水库 最新数据
	From yzsq_RSVR_R
	Where STCD='62705450' order by TM desc
 )t21
 
 
 union
  SELECT * from (
	 select top 1 '10000001' as ZBM,ryh_sysw as SSW,ryh_xysw as XSW,null as LN,time as TM--润扬河 最新数据
	From yzsl_ylh_shchuan
	 order by TM desc
 )t22
 union
  SELECT * from (
	 select top 1  '10000003' as ZBM,yyh_sysw as SSW,yyh_xysw as XSW,null as LN,time as TM--仪扬河 最新数据
	From yzsl_ylh_shchuan
	 order by TM desc
 )t23
 union
  SELECT * from (
	 select top 1 '10000002' as ZBM,sysw as SSW,xysw as XSW,ts as LN,time as TM--西闸站 最新数据
	From yzsl_ysh_shchuan
	 order by TM desc
 )t24