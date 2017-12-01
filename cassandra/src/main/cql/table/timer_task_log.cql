CREATE TABLE xlink.timer_task_log (
	task_id varchar,
	execute_time timestamp,
	id varchar,
    task_name varchar,
	owner varchar,
	action varchar,
	status int,
	error varchar,
	result varchar,
	PRIMARY KEY (task_id, execute_time)
) WITH CLUSTERING ORDER BY ( execute_time DESC ) AND comment = '定时任务执行日志';
