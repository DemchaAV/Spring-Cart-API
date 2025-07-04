alter table orders
    modify created_at datetime default CURRENT_TIMESTAMP not null;