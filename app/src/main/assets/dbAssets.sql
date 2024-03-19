
Create table "exercises" (
  "id"  integer primary key,
  "name" text not null,
  "category" text not null,
  "description" text not null,
  "image" text);


  insert into "exercises" ("name","category","description","image")
  values
     ("push-ups","chest","bep",null),
     ("pull-ups","back","ber",null);
