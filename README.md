# Buyistic

![Alt](https://repobeats.axiom.co/api/embed/0f7e9b27c609852e34a460922e6f2bfa73001491.svg "Repobeats analytics image")

## Setup

### MySQL

```sql
create database buyistic;
create table buyistic.accounts
(
    uuid     text not null,
    username text not null,
    password text not null
);
create table buyistic.items
(
    imgurl        text  not null,
    nowprice      float not null,
    rawprice      float not null,
    title         text  not null,
    detail        text  not null,
    id            int auto_increment primary key,
    categories    text  not null,
    type          int   not null,
    introductions text  not null
);
create table buyistic.orders
(
    orderid    text      not null,
    itemid     int       not null,
    createTime timestamp not null,
    status     int       not null,
    price      double    not null,
    userid     text      not null
);
```

### Springboot

```bash
gradle build
java -jar Buyistic.jar
```

### Vue

```bash
npm run dev
```

## HTTP APIs

### for `Foreground`

- GET `/get_item?id=${ITEMID}`

- GET `/get_items`

- GET `/get_user?userid=${USERID}`

- GET `/buyit?userid=${USERID}&itemid=${ITEMID}&price=${PRICE_NUMBER}`

- GET `/cancel_order?orderid=${ORDERID}`

- GET `/get_order?orderid=${ORDERID}`

- GET `/orders?userid=${USERID}`

- POST `/login?username=${USERNAME}?password=${PASSWORD}`
