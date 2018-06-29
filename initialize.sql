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
        ServerPeople int,
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