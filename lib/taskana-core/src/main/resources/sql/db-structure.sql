CREATE TABLE IF NOT EXISTS TASK( 
	ID varchar(255) NOT NULL,
	TENANT_ID varchar(255) NULL,
	CREATED TIMESTAMP NULL,
	CLAIMED TIMESTAMP NULL,
	COMPLETED TIMESTAMP NULL,
	MODIFIED TIMESTAMP NULL,
	PLANNED TIMESTAMP NULL,
	DUE TIMESTAMP NULL,
	NAME varchar(1024) NULL,
	DESCRIPTION varchar(4096) NULL,
	PRIORITY INT NULL,
	STATE varchar(20) NULL,
	TYPE varchar(255) NULL,
	WORKBASKETID varchar(255) NULL,
	OWNER varchar(255) NULL,
	IS_READ bit NOT NULL,
	IS_TRANSFERRED bit NOT NULL,
	PRIMARY KEY (ID)
);

CREATE TABLE IF NOT EXISTS WORKBASKET(
	ID varchar(255) NOT NULL,
	TENANT_ID varchar(255) NULL,
	CREATED TIMESTAMP NULL,
	MODIFIED TIMESTAMP NULL,
	NAME varchar(255) NULL,
	DESCRIPTION varchar(255) NULL,
	OWNER varchar(255) NULL,
	PRIMARY KEY (ID),
	CONSTRAINT UC_NAME UNIQUE (NAME)
);

CREATE TABLE IF NOT EXISTS DISTRIBUTION_TARGETS(
	SOURCE_ID varchar(255) NOT NULL,
	TARGET_ID varchar(255) NOT NULL,
	PRIMARY KEY (SOURCE_ID, TARGET_ID)
);

CREATE TABLE IF NOT EXISTS BUSINESS_CLASSIFICATION(
	ID varchar(255) NOT NULL,
	TENANT_ID varchar(255) NULL,
	PARENT_CLASSIFICATION_ID varchar(255),
	CREATED DATE NULL,
	MODIFIED DATE NULL,
	NAME varchar(255) NULL,
	DESCRIPTION varchar(255) NULL,
	PRIORITY INT NULL,
	SERVICE_LEVEL varchar(255) NULL,
	PRIMARY KEY (ID)
);

CREATE TABLE IF NOT EXISTS WORKBASKET_ACCESS_LIST(
	ID varchar(255) NOT NULL,
	WORKBASKET_ID varchar(255) NOT NULL,
	USER_ID varchar(255) NULL,
	GROUP_ID varchar(255) NULL,
	READ bit NOT NULL,
	OPEN bit NOT NULL,
	APPEND bit NOT NULL,
	TRANSFER bit NOT NULL,
	DISTRIBUTE bit NOT NULL,
	PRIMARY KEY (ID)
);