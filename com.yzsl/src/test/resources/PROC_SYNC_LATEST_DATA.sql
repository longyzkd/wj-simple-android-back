USE [SQB]
GO
/****** Object:  StoredProcedure [dbo].[PROC_SYNC_LATEST_DATA]    Script Date: 08/31/2016 16:37:40 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER proc [dbo].[PROC_SYNC_LATEST_DATA]
as
     --oracle
	 exec PROC_SYNC_ONE_LATEST_DATA 'YZSQ_PPTN_R', ''  ,'oracle';
	 exec PROC_SYNC_ONE_LATEST_DATA 'YZSQ_PUMP_R', ''  ,'oracle';
	 exec PROC_SYNC_ONE_LATEST_DATA 'YZSQ_RIVER_R', ''  ,'oracle';
	 exec PROC_SYNC_ONE_LATEST_DATA 'YZSQ_RIVER_R', ''  ,'oracle';
	 exec PROC_SYNC_ONE_LATEST_DATA 'YZSQ_STORM_R', ''  ,'oracle';      
	 exec PROC_SYNC_ONE_LATEST_DATA 'YZSQ_TIDE_R', ''  ,'oracle';      
	 exec PROC_SYNC_ONE_LATEST_DATA 'YZSQ_WAS_R', ''  ,'oracle';
	 exec PROC_SYNC_ONE_LATEST_DATA 'YZSQ_WDWV_R', ''  ,'oracle';
	 exec PROC_SYNC_ONE_LATEST_DATA 'YZSQ_GATE_R', ''  ,'oracle';
	 
	 --mysql
     exec PROC_SYNC_ONE_LATEST_DATA 'yzsl_gzjzz_shchuan', ''  ,'mysql';
	 exec PROC_SYNC_ONE_LATEST_DATA 'yzsl_ylh_shchuan', ''  ,'mysql';
	 exec PROC_SYNC_ONE_LATEST_DATA 'yzsl_ysh_shchuan', ''  ,'mysql';
	 
	 --sqlserver
	 exec PROC_SYNC_ONE_LATEST_DATA 'alarm', ''  ,'sqlserver';
	 exec PROC_SYNC_ONE_LATEST_DATA 'dl', ''  ,'sqlserver';
	 exec PROC_SYNC_ONE_LATEST_DATA 'FIXALARMS', ''  ,'sqlserver'; 	
	 exec PROC_SYNC_ONE_LATEST_DATA 'maxmin', ''  ,'sqlserver'; 
	 exec PROC_SYNC_ONE_LATEST_DATA 'mvalue', ''  ,'sqlserver';
	 exec PROC_SYNC_ONE_LATEST_DATA 'operation', ''  ,'sqlserver';
	 --exec PROC_SYNC_ONE_LATEST_DATA 'sysdiagrams', ''  ,'sqlserver';
	 exec PROC_SYNC_ONE_LATEST_DATA 'water', ''  ,'sqlserver';
	 exec PROC_SYNC_ONE_LATEST_DATA 'zm', ''  ,'sqlserver';
	 