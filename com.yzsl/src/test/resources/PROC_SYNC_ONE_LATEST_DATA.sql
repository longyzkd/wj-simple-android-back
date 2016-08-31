USE [SQB]
GO
/****** Object:  StoredProcedure [dbo].[PROC_SYNC_ONE_LATEST_DATA]    Script Date: 08/31/2016 16:35:14 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO


-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	查询各个表的某闸的最大时间。远程表里，大于这个时间的结果返回过来。在本地表插入返回记录。-插入完成。视图再查就行
-- =============================================
ALTER PROCEDURE [dbo].[PROC_SYNC_ONE_LATEST_DATA] ---单表单闸的
	 @tableName varchar(50),
	 @STCD varchar(50),
	 @dbType varchar(50)
 AS
      BEGIN
          DECLARE @latestTime datetime
          DECLARE @remoteTableName varchar(50)
          DECLARE @sql nvarchar(1000);
          DECLARE @insertSql nvarchar(1000); --远程表copy到本地
          DECLARE @dat nvarchar(50);
          DECLARE @addDate int
          DECLARE @fieldName varchar(50)

   --当前日期前10天
   SET @addDate = 10
   IF (@dbType='oracle')
		BEGIN
                    --取本表满足条件的最近时间
                    SET @sql='select @latestTime=MAX( TM ) from '+ @tableName ;			
                    exec sp_executesql @sql,N'@latestTime datetime output',@latestTime out 
                    --没有记录时间，则以当前日期前10天为准
                    if (@latestTime = null)
                       select @dat = convert(varchar(10),getdate() - @addDate,120) + ' 00:00:00' 
                    else
                       select @dat = convert(varchar(50),@latestTime,120)
                    --源数据表与目的表的转换
		    SET @remoteTableName = REPLACE (@tableName,'YZSQ','YZSQ.ST')		
	            SET @insertSql=' insert into '+@tableName+' select * from '+
	            '  openquery(yzsq,''select * from  '+@remoteTableName+' where  tm>'''''+@dat+''''''')'
		    exec(@insertSql)
		END
	ELSE IF(@dbType='mysql')
		BEGIN
                    --取本表满足条件的最近时间
                    SET @sql='select @latestTime=MAX( TIME ) from '+ @tableName ;			
                    exec sp_executesql @sql,N'@latestTime datetime output',@latestTime out 
                    --没有记录时间，则以当前日期前10天为准
                    if (@latestTime = null)
                       select @dat = convert(varchar(10),getdate() - @addDate,120) + ' 00:00:00' 
                    else
                       select @dat = convert(varchar(50),@latestTime,120)
                    --查询插入
	            SET @insertSql=' insert into '+@tableName+' select * from '+
	            '  openquery(mysql,''select * from  '+@tableName+' where  time>'''''+@dat+''''''')'
		    exec(@insertSql)
				
		END
	ELSE --sqlserver
		BEGIN
                    --取本表满足条件的最近时间
                    SET @fieldName = 'DATETIME' 
                    if (@tableName = 'FIXALARMS')
                         SET @fieldName = 'ALM_NATIVETIMEIN'
                    if (@tableName = 'maxmin')
                         SET @fieldName = 'date'
                    if (@tableName = 'sysdiagrams')
                         SET @fieldName = 'T'
                    SET @sql='select @latestTime=MAX( ' + @fieldName + ') from '+ @tableName ;	


		
                    exec sp_executesql @sql,N'@latestTime datetime output',@latestTime out 
                    --没有记录时间，则以当前日期前10天为准
                    if (@latestTime = null)
                       select @dat = convert(varchar(10),getdate() - @addDate,120) + ' 00:00:00' 
                    else
                       select @dat = convert(varchar(50),@latestTime,120)
                    --查询插入
	            SET @insertSql=' insert into '+@tableName+' select * from '+
	            '  openquery("192.168.100.101",''select * from  '+@tableName+' where  ' + @fieldName + '>'''''+@dat+''''''')'	
		    exec(@insertSql)
		END   
		
	 END