MyProject -> SmartFitAI : Provided By @Dardalyan (name: Enes Burak Özdemir)
My Protfolio Spring Boot Project

 !! All the code lines are belongs to @Dardalyan !!

    You will see in this project:
        * How to handle JWT authentication
        * How to Use services , repositories , rest controllers and also configurations
        * You can get some info how to calculate BMR and Calorie Bound of your body in this project
        * You can create your own exercise program and etc.
        * Also you will see how can easily handle update methods by just "Field" checking !
        * You will see the descriptions in "//" comment lines

I have used NoSQL

  * DB : -> MongoDB

USER:
    id (INT AUTO_INCREMENT PRIMARY KEY)
    username (VARCHAR UNIQUE KEY)
    email (VARCHAR UNIQUE KEY )
    password (VARCHAR)

CONTACT:
    user_id (FOREIGN KEY REFERENCES USER(id))
    phone (VARCHAR UNIQUE KEY)
    name (VARCHAR)
    surname (VARCHAR)

INFO:
    user_id (FOREIGN KEY REFERENCES USER(id))
    age (INT)
    sex (ENUM)
    weight (DOUBLE)
    height (DOUBLE)
    target (ENUM)
    bmr (DOUBLE)
    activity_intense (ENUM)
    calorie (INT)
    target_body_parts (ARRAY)



PROGRAM:
     user_id (FOREIGN KEY REFERENCES USER(id))
     monday (ARRAY)
     tuesday (ARRAY)
     wednesday (ARRAY)
     thursday (ARRAY)
     friday (ARRAY)
     saturday (ARRAY)
     sunday (ARRAY)

EXERCISE:
    name (VARCHAR PRIMARY KEY)
    bodypart (VARCHAR)






