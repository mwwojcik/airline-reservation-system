CREATE TABLE customers (
   id INT  NOT NULL,
   name VARCHAR (45),
   email VARCHAR (45),
   PRIMARY KEY (ID)
);

CREATE TABLE reservations(
    id UUID PRIMARY KEY,
    flight_id UUID,
    customer_id UUID,
    departure_date datetime,
    status varchar(50),
    price DECIMAL,
    seat int,
    parent_res_id UUID,
    currently_locked int,
    reserved_this_month int,
    rescheduled_so_far int
);