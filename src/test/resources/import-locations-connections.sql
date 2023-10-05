insert into locations (id, name, address, latitude, longitude)
values (1, 'Sky Tower', 'Powstańców Śląskich 95', 51.09557216113358, 17.019321302937936);

insert into locations (id, name, address, latitude, longitude)
values (2, 'Hydropolis', 'Na Grobli 17', 51.105128704307575, 17.056662398142603);

insert into locations (id, name, address, latitude, longitude)
values (3, 'Politechnika Wrocławska', 'Wybrzeże Stanisława Wyspiańskiego 27', 51.10750797410564, 17.061782279956137);

insert into locations (id, name, address, latitude, longitude)
values (4, 'Pod Trumienka', 'Curie-Skłodowskiej 51', 51.11006178612196, 17.06320845223983);

insert into locations (id, name, address, latitude, longitude)
values (5, 'Wrocławski Rynek', 'Rynek', 51.11068511994322, 17.030924671282094);

insert into locations (id, name, address, latitude, longitude)
values (6, 'Wroclavia', 'Sucha 1', 51.097887472078106, 17.034623266899263);

INSERT INTO locations_connection (id, start_location_id, end_location_id, distance_in_meters, time_on_foot_in_sec, time_by_bus_in_sec, time_by_car_in_sec)
VALUES (1, 1, 2, 900, 300, 250, 120);

INSERT INTO locations_connection (id, start_location_id, end_location_id, distance_in_meters, time_on_foot_in_sec, time_by_bus_in_sec, time_by_car_in_sec)
VALUES (2, 1, 3, 900, 300, 250, 120);

INSERT INTO locations_connection (id, start_location_id, end_location_id, distance_in_meters, time_on_foot_in_sec, time_by_bus_in_sec, time_by_car_in_sec)
VALUES (3, 2, 3, 900, 300, 250, 120);

INSERT INTO locations_connection (id, start_location_id, end_location_id, distance_in_meters, time_on_foot_in_sec, time_by_bus_in_sec, time_by_car_in_sec)
VALUES (4, 3, 2, 900, 300, 250, 120);