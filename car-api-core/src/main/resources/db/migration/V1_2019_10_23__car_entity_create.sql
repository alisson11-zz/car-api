CREATE TABLE car
(
  id uuid NOT NULL,
  name varchar(255) NOT NULL,
  brand varchar(255) NOT NULL,
  model varchar(255) NOT NULL,
  manufacturing_date DATE NOT NULL,
  average_city_consumption NUMERIC(19, 3) NOT NULL,
  average_highway_consumption NUMERIC(19, 3) NOT NULL,
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
  updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
  CONSTRAINT car_pkey PRIMARY KEY (id)
);