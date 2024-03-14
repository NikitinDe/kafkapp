CREATE TABLE clients(
id BIGSERIAL PRIMARY KEY  ,
full_name varchar (255),
phone  varchar (255),
birthday  Date,
message_send  Boolean
);