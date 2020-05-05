delete from ticket;
delete from application;
delete from release;

insert into application (a_id, a_desc, a_name, a_owner) values (1, 'Description of Test Application 1', 'Test Application 1', 'Unknown Owner 1');
insert into application (a_id, a_desc, a_name, a_owner) values (2, 'Description of Test Application 2', 'Test Application 2', 'Unknown Owner 2');
insert into application (a_id, a_desc, a_name, a_owner) values (3, 'Description of Test Application 3', 'Test Application 3', 'Unknown Owner 3');
insert into application (a_id, a_desc, a_name, a_owner) values (4, 'Description of Test Application 4', 'Test Application 4', 'Unknown Owner 4');

insert into ticket  (id, title, description, status, application_id) values (1, 'Ticket 1', 'Description 1', 'DRAFT', null);
insert into ticket  (id, title, description, status, application_id) values (2, 'Ticket 2', 'Description 2', 'DRAFT', null);
insert into ticket  (id, title, description, status, application_id) values (3, 'Ticket 3', 'Description 3', 'DRAFT', 3);
insert into ticket  (id, title, description, status, application_id) values (4, 'Ticket 4', 'Description 4', 'DRAFT', null);
insert into ticket  (id, title, description, status, application_id) values (5, 'Ticket 5', 'Description 5', 'DRAFT', null);

insert into release (id, release_date, description) values (1, '2025-09-01', 'Major release of v2.0');
