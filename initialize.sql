create schema if not exists cookbookdatabase;

create user MacroHard@'%' identified by '123456';
grant all privileges on cookbookdatabase.* to MacroHard@'%' with grant option;
ALTER USER 'MacroHard'@'%' IDENTIFIED WITH mysql_native_password BY '123456';
flush privileges;

use cookbookdatabase;


create table if not exists Ingredient(
		Name varchar(45) primary key
        );
        
create table if not exists IngredientUsingInfo(
		
        Name varchar(45) not null,
        RecipeID int not null ,
        CookingWay varchar(45),
        Weight double,
        Unit varchar(45) default 'g',
        primary key (Name,RecipeID)
        
);

create table if not exists Recipe(
		RecipeID int primary key auto_increment,
        Name varchar(45),
        PrepTime int,
        AccountID int,
        CookingTime int,
        ServingPeople int,
        Category varchar(45)
);



create table if not exists PreparationStep(
		RecipeID int,
        StepNo int,
        PreparationContext text,
        primary key(RecipeID, StepNo)
);
        
create table if not exists  User(
		AccountID int primary key auto_increment,
        Username varchar(45) not null,
        password varchar(45) not null
        
);

create table if not exists Comment(
		
        CommentNo int auto_increment,
        RecipeID int,
        AccountID int,
        Content text,
        primary key(CommentNo,RecipeID, AccountID)
);

insert into Ingredient value ('cornstarch');
insert into Ingredient value ('soy sauce');
insert into Ingredient value ('chicken breast');
insert into Ingredient value ('Shaoxin rice wine');
insert into Ingredient value ('sugar');
insert into Ingredient value ('chicken stock');
insert into Ingredient value ('Chiangang vinegar');
insert into Ingredient value ('sesame oil');
insert into Ingredient value ('dried red chilis');
insert into Ingredient value ('scallions');
insert into Ingredient value ('garlic');
insert into Ingredient value ('ginger');
insert into Ingredient value ('peanuts');
insert into Ingredient value ('pork belly');
insert into Ingredient value ('cooking oil');
insert into Ingredient value ('brown sugar');
insert into Ingredient value ('light soy sauce');
insert into Ingredient value ('dark soy sauce');
insert into Ingredient value ('chicken stock or water');
insert into Ingredient value ('potato noodles');
insert into Ingredient value ('spring onion');
insert into Ingredient value ('coriander');
insert into Ingredient value ('pickled Sichuan vegetable');
insert into Ingredient value ('peanut oil');
insert into Ingredient value ('Sichuan peppercorn powder');
insert into Ingredient value ('Chinese five spicy powder');
insert into Ingredient value ('chili powder');
insert into Ingredient value ('vinegar');
insert into Ingredient value ('salt');
insert into Ingredient value ('Rape');
insert into Ingredient value ('egg');
insert into Ingredient value ('onion');
insert into Ingredient value ('Seafood Soy Sauce');
insert into Ingredient value ('whiht sugar');
insert into Ingredient value ('Star Aniseed');
insert into Ingredient value ('cinnamon');
insert into Ingredient value ('drumstick');
insert into Ingredient value ('cooking wine');
insert into Ingredient value ('pepper');
insert into Ingredient value ('lemon juice');
insert into Ingredient value ('honey');
insert into Ingredient value ('five spice powder');
insert into Ingredient value ('chicken wing');
insert into Ingredient value ('green pepper');
insert into Ingredient value ('red pepper');
insert into Ingredient value ('oil');
insert into Ingredient value ('beanpaste');
insert into Ingredient value ('rock candy');
insert into Ingredient value ('chicken powder');
insert into Ingredient value ('myrcia');
insert into Ingredient value ('Sichuan Pepper');
insert into Ingredient value ('chilli');
insert into Ingredient value ('beer');
insert into Ingredient value ('aromatic vinegar');
insert into Ingredient value ('chive');
insert into Ingredient value ('cucumber');
insert into Ingredient value ('Jellyfish');
insert into Ingredient value ('carrot');
insert into Ingredient value ('rice vinegar');
insert into Ingredient value ('capsicol');
insert into Ingredient value ('tomato');
insert into Ingredient value ('vegetable oil');
insert into Ingredient value ('LuHuiTong');

insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('cornstarch',1,1.0,'tablespoon','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('soy sauce',1,4.0,'tablespoon','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('chicken breast',1,0.5,'kg','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('Shaoxin rice wine',1,3.0,'tablespoon','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('sugar',1,2.0,'tablespoon','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('dried red chilis',1,12.0,'pieces','stemmed, halved crosswise, and seeded');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('chicken stock',1,3.0,'tablespoon','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('Chiangang vinegar',1,4.0,'teaspoon','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('sesame oil',1,4.0,'tablespoon','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('dark soy sauce',1,2.0,'teaspoon','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('peanut oil',1,3.0,'tablespoon','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('scallions',1,5.0,'pieces','white part onyl, thickly sliced crosswise');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('garlic',1,1.0,'cloves','peeled, thinly sliced');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('ginger',1,0.5,'pieces','peeled, minced');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('peanuts',1,0.5,'cups','peeled, thinly sliced');

insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('pork belly',2, 0.5, 'kg','cut into 2cm pieces');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('cooking oil',2, 2.0, 'tablespoon','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('brown sugar',2, 1.0, 'tablespoon','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('Shaoxin rice wine',2, 3.0, 'tablespoon','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('light soy sauce',2, 1.0, 'tablespoon','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('dark soy sauce',2, 0.5, 'tablespoon','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('chicken stock or water',2, 2.0, 'cups','');

insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('potato noodles',3, 1.0, 'bunch','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('peanuts',3, 2.0, 'tablespoon', 'roasted');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('spring onion',3, 1.0, 'tablespoon', 'chopped');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('coriander',3, 1.0, 'tablespoon', 'chopped');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('pickled Sichuan vegetable',3, 2.0, 'tablespoon', 'chopped');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('garlic',3, 3.0, 'cloves', 'mashed');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('peanut oil',3, 0.5, 'tablespoon','') ;
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('Sichuan peppercorn powder',3, 0.5, 'teaspoon','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('Chinese five spicy powder',3, 0.5, 'teaspoon','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('vinegar',3, 1.0, 'tablespoon','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('chili powder',3, 1.0, 'teaspoon','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('light soy sauce',3, 1.0, 'tablespoon','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('salt',3, 1.0, 'teaspoon','');

insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('pork belly',4, 0.5, 'kg','choose the suitable one');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('Rape',4, 0.2, 'kg','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('egg',4, 3.0, '','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('onion',4, 1.0, '', 'chopped');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('Seafood Soy Sauce',4, 1.0, 'tablespoon','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('light soy sauce',4, 1.0, 'teaspoon','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('whiht suger',4, 1.0, 'tablespoon','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('Star Aniseed', 4,1.0, 'teaspoon','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values (' cinnamon',4, 1.0, 'teaspoon','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values (' dried red chilis', 4,1.0, 'teaspoon','');

insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('drumstick',5, 2.0, ' ','dislodge the bone and beat it');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('soy sauce',5, 4.0, 'tablespoon','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('dark soy sauce',5, 1.0, 'tablespoon','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('cooking wine',5, 3.0, 'tablespoon','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('pepper',5, 0.25, 'teaspoon','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('lemon juice',5, 0.5, 'tablespoon','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('honey',5, 1.0, 'tablespoon','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('five spice powder',5,0.25, 'teaspoon','');

insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('drumstick',6, 2.0, ' ','cut into piece');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('chicken wing',6, 2.0, ' ','cut into piece');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('potato',6, 2.0, ' ','peeling and cut into pieces ');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('green pepper',6, 1.0, ' ','cut into pieces');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('red pepper',6, 1.0,' ','cut into pieces');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('onion',6, 1.0, ' ','cut into pieces');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('oil',6, 0.02, 'liter','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('beanpaste', 6,2.0, 'tablespoon','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('rock candy',6, 0.015, 'kg','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('salt',6, 0.001, 'kg','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('chicken powder ',6, 0.001, 'kg','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('light soy sauce',6, 0.01, 'liter','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('Star Aniseed',6, 2.0, ' ','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('myrcia',6, 2.0, ' ','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('Sichuan Pepper',6, 0.05, 'kg','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('chilli',6, 2.0, 'piece','cut into pieces');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('beer',6, 1.0, 'tin','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('ginger',6, 1.0, 'piece','cut into pieces');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('garlic',6, 2.0, 'piece','cut into pieces');

insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('Potato',7, 0.3, 'kg','peeling, cut into threadlet and wash');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('chilli',7,  0.1, 'kg','wash cut into threadlet');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('salt',7,  0.5, 'teaspoon','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('chicken powder',7,  0.5, 'teaspoon','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('aromatic vinegar',7,  4.0, 'teaspoon','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('garlic',7,  2.0, 'pieces','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('chive', 7, 0.01, 'kg','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('oil',7,  0.02, 'Liter','');

insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('cucumber ',8, 0.4, 'kg','peeling, cut into threadlet');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('Jellyfish',8, 0.15, 'kg','peeling, cut into threadlet');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('carrot',8, 0.1, 'kg','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('soy sauce', 8,0.002, 'kg','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('salt',8, 0.004, 'kg','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('garlic',8, 0.015, 'kg','mashed');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('rice vinegar',8, 0.04, 'kg','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('capsicol',8, 0.005, 'kg','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('white suger', 8,0.02, 'kg','');

insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('egg',9, 3.0, ' ','stir');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('tomato',9, 2.0, ' ','cut into piece');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('salt',9, 0.5, 'teaspoon','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('light soy sauce',9, 0.5, 'tablespoon','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('white suger',9, 1.0, 'teaspoon','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('chive',9, 1.0, 'piece','cut into piece');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('vegetable oil',9, 4.0, 'tablespoon','');

insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('LuHuiTong',10, 1.0, ' ','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('dark soy sauce',10, 0.2,'tablespoon','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('salt',10, 0.5, 'teaspoon','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('light soy sauce',10, 0.5, 'tablespoon','');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('garlic',10, 2.0, 'piece','cut into piece');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('ginger',10,2.0, 'piece','cut into piece');
insert into IngredientUsingInfo(Name,recipeID,weight,unit,CookingWay) values ('vegetable oil',10, 4.0, 'tablespoon','');

insert into Recipe values (1,'Gong Bao Jiding',30,1,10,4,'Sichuan Dish');
insert into Recipe values (2,'Hong Shao Rou',5,1,100,4,'Hunan Dish');
insert into Recipe values (3,'Suan La Fen',30,1,5,2,'Sichuan Dish');
insert into Recipe values (4,'Tai Wan Lu Rou',10,1,60,2,'TaiWan Dish');
insert into Recipe values (5,'Zhao Shao Ji Tui',10,1,30,2,'Japanese Dish');
insert into Recipe values (6,'Da Pan Ji',15,1,30,2,'WuLuMuQi Dish');
insert into Recipe values (7,'Suan La Tu Dou Si',5,1,10,2,'SiChuan Dish');
insert into Recipe values (8,'Zhe Si Ban Huang Gua',10,1,10,2,'JiaChang Dish');
insert into Recipe values (9,'Xi Hong Shi Chao Ji Dan',10,1,10,2,'ShangHai Dish');
insert into Recipe values (10,'Lu Hui Tong Gu Hui Ban Fan',60,1,60,5,'LuHuiTong Dish');

insert into PreparationStep values (1,1,'Mix together cornstarch and 1 tbsp. of the soy sauce in a medium bowl.');
insert into PreparationStep values (1,2,'Add chicken, toss well, and set aside to marinate for 30 minutes.');
insert into PreparationStep values (1,3,'Meanwhile, mix together the remaining 3 tbsp. soy sauce, rice wine, sugar, stock, vinegar, sesame oil, and dark soy sauce.');
insert into PreparationStep values (1,4,'Set aside.');
insert into PreparationStep values (1,5,'Heat peanut oil in a wok or large nonstick skillet over high heat until just beginning to smoke.');
insert into PreparationStep values (1,6,'Add chilis, half the scallions, garlic, ginger, and chicken and stir-fry until chicken is golden, 3-5 minutes.');
insert into PreparationStep values (1,7,'Add soy sauce mixture and stir-fry until sauce thickens, about 2 minutes.');
insert into PreparationStep values (1,8,'Stir in peanuts.');
insert into PreparationStep values (1,9,'Garnish with remaining scallions.');

insert into PreparationStep values (2,1,'Bring a pot of water to a boil and blanch the pork for a couple minutes.');
insert into PreparationStep values (2,2,'Take the pork out of the pot and set aside.');
insert into PreparationStep values (2,3,'Over low heat, add oil and sugar to your wok.');
insert into PreparationStep values (2,4,'Melt the sugar slightly and add the pork.');
insert into PreparationStep values (2,5,'Raise the heat to medium and cook until the pork is lightly browned.');
insert into PreparationStep values (2,6,'Turn the heat back down to low and add cooking wine, light soy sauce, dark soy sauce, and chicken stock.');
insert into PreparationStep values (2,7,'Cover and simmer for about 60 minutes to 90 minutes until pork is fork tender.');
insert into PreparationStep values (2,8,'Every 5-10 minutes, stir to prevent burning and add water if it gets too dry.');
insert into PreparationStep values (2,9,'Once the pork is fork tender, if there is still a lot of visible liquid, uncover the wok, turn up the heat, and stir continuously the sauce has reduced to a glistening coating.');
        
insert into PreparationStep values (3,1,'Soak the sweet potato noodles with hot water around 30 minutes.');
insert into PreparationStep values (3,2,'Transfer out and set aside.');
insert into PreparationStep values (3,3,'In the serving bowls and mix all the seasonings.');
insert into PreparationStep values (3,4,'Heat up peanuts oil in pan to stir-fry the mashed garlic until aroma.');
insert into PreparationStep values (3,5,'Mix the garlic oil with the seasonings.');
insert into PreparationStep values (3,6,'Add some spring onions and corianders in serving bowls.');
insert into PreparationStep values (3,7,'Pour in boiling water or stock to tune the seasonings.');
insert into PreparationStep values (3,8,'Add vinegar and light soy sauce.');
insert into PreparationStep values (3,9,'Mix well and set aside.');
insert into PreparationStep values (3,10,'Cook soaked sweet potato noodles around 3~5 minutes until you can break the noodles with your fingers.');
insert into PreparationStep values (3,11,'Transfer the noodles out to the serving bowls and then add top with pickled vegetables, roasted peanuts and chopped spring onions and coriander. ');

insert into PreparationStep values (4,1,'Boil the rape and cut the prok belly into small one.');
insert into PreparationStep values (4,2,'Boil the prok belly.');
insert into PreparationStep values (4,3,'Turn the fire big, cook the onion first and then put the prok belly in.');
insert into PreparationStep values (4,4,'Put the boiling water in.');
insert into PreparationStep values (4,5,'Turn the fire small and put the dosing,egg and seafood soy sauce in.');
insert into PreparationStep values (4,6,'Put the light soy sauce in and braise for half an hour.');
insert into PreparationStep values (4,7,'Put the suger in');
insert into PreparationStep values (4,8,'Fry it and use big fire to evaporative water');
insert into PreparationStep values (4,9,'Finish and put it in to bowl');

insert into PreparationStep values (5,1,'Put 1 tablespoon soy sauce, 1 tablespoon dark soy sauce, 1 tablespoon cooking wine, 1/4 teaspoon pepper and 1/2 tablespoon lemon juice into a bowl and homogenize it.');
insert into PreparationStep values (5,2,'Use the sauce to pickle the drumstick with 2 hours');
insert into PreparationStep values (5,3,'Homogenize the other sauce');
insert into PreparationStep values (5,4,'Put little oil and wait for 1 mins, and then put the drumstick in.');
insert into PreparationStep values (5,5,'When the drumsticks colour change, put the sauce in.');
insert into PreparationStep values (5,6,'Fry for 5 mins and then fry another side for 5 mins.');
insert into PreparationStep values (5,7,'Put it into the plate.');

insert into PreparationStep values (6,1,'Put cool water into pan and boid the chicken meat and then get out.');
insert into PreparationStep values (6,2,'Put oil in and put myrcia,Star Aniseed,Sichuan Pepper,chilli,ginger and garlic and then fry it.');
insert into PreparationStep values (6,3,'Put beanpaste in and fry.');
insert into PreparationStep values (6,4,'Put the meat in and fry.');
insert into PreparationStep values (6,5,'Put the rock candy and light soy sauce in and fry.');
insert into PreparationStep values (6,6,'Put beer and a bowl of water in and braise for 15 mins');
insert into PreparationStep values (6,7,'Put the potato in and braise for 15 mins');
insert into PreparationStep values (6,8,'Put the onion,pepper in and fry and then use big fire to evaporative water');
insert into PreparationStep values (6,9,'Put the salt and chicken powder in and fry');
insert into PreparationStep values (6,10,'Put it into plate');

insert into PreparationStep values (7,1,'Put the oil into the pan and fry, then put the chilli and garlic into it.');
insert into PreparationStep values (7,2,'Put the potato into it and fry for nearlt 3 mins.');
insert into PreparationStep values (7,3,'Put the salt, chicken powder and aromatic vinegar to fry.');
insert into PreparationStep values (7,4,'Finally put into the plate and put the chive on it.');

insert into PreparationStep values (8,1,'Put the mashed garlic and white suger together.');
insert into PreparationStep values (8,2,'Put the rice vinger, salt ,soy sauce and capsicol in.');
insert into PreparationStep values (8,3,'Put the cumumber,jellyfish and carrot together and then put the sauce on it.');

insert into PreparationStep values (9,1,'Put the stired egg into pan with oil and fry to solid and then put into bowl.');
insert into PreparationStep values (9,2,'Put the tomato into the the pan with oil and fry.');
insert into PreparationStep values (9,3,'Put the egg in and fry.');
insert into PreparationStep values (9,4,'Put the sauce,white suger and salt in and fry.');
insert into PreparationStep values (9,5,'Put it into the plate and put the chive on it.');


insert into PreparationStep values (10,1,'Use the big big big fire to burn LuHuiTong into ash.');
insert into PreparationStep values (10,2,'Put the garlic,ginger into he pan with oil and fry.');
insert into PreparationStep values (10,3,'Put the ash into it and fry.');
insert into PreparationStep values (10,4,'Put the sauce,salt into it and boil for 10 mins.');
insert into PreparationStep values (10,5,'Then you have the LuHuiTongJingZhiGuHui which can be eaten with rice.');
