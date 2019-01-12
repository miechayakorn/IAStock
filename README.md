# IAStock Project  

## Sitemap
**/Home**  
**/ShowStock (LineItem)**  
**/PrintStock (History)**  

***Database Name:*** IAStock  
***UserName:*** ia  
***Password:*** ia 

## Path Default DB
C:\Users\Acer_E5\AppData\Roaming\NetBeans\Derby  
IAStock/Derby  

***SQL***
```sql
create table years (
yearStock varchar(4) primary key,
status int check (status in (0,1)) not null);
```
```sql
create table items (
itemId varchar(30) primary key,
itemName varchar(100) not null,
itemTotal int not null,
price double not null,
yearStock varchar(4) not null,
FOREIGN KEY (yearStock) REFERENCES years(yearStock));
```
```sql
create table history (
historyId int GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) primary key,
itemId varchar(30) not null,
quantity int not null,
type varchar(40) not null,
timestamp timestamp not null,
yearStock varchar(4) not null,
annotation varchar(100),
FOREIGN KEY (yearStock) REFERENCES years(yearStock),
FOREIGN KEY (itemId) REFERENCES items(itemId));
```
