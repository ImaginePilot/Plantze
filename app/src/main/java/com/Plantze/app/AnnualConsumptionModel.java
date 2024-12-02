package com.plantze.app;

public class AnnualConsumptionModel {

    public  String[] questions= new String[]{     //An array of all the questions
            "Do you own or regularly use a car?",
            "What type of car do you drive?",
            "How many kilometers/miles do you drive per year?",
            "How often do you use public transportation (bus, train,subway)?",
            "How much time do you spend on public transport per week (bus, train, subway)?",
            "How many short-haul flights (less than 1,500 km / 932 miles) have you taken in the past year?",
            "How many long-haul flights (more than 1,500 km / 932 miles) have you taken the past year?",
            "What best describes your diet?",
            "How often do you eat beef?",
            "How often do you eat pork?",
            "How often do you eat chicken?",
            "How often do you eat fish/seafood?",
            "How often do you waste food or throw away uneaten leftovers?",
            "What type of home do you live in?",
            "How many people live in your household?",
            "What is the size of your home?",
            "What type of energy do you use to heat your home?",
            "What is your average monthly electricity bill?",
            "What type of energy do you use to heat water in your home?",
            "Do you use any renewable energy sources for electricity or heating (e.g., solar,wind)?",
            "How often do you buy new clothes?",
            "Do you buy second-hand or eco-friendly products?",
            "How many electronic devices (phones, laptops, etc.) have you purchased in the past year?",
            "How often do you recycle?"
    };
    public String[][] answerChoices = new String[][]  {  //A 2d array of Answers
            {"Yes","No"},
            {"Gasoline","Diesel","Hybrid","Electric","I don’t know"},
            {"Up to 5,000 km (3,000 miles)","5,000-10,000 km (3,000–6,000 miles)","10,000–15,000 km (6,000–9,000 miles)","15,000–20,000 km (9,000–12,000 miles)","20,000–25,000 km (12,000–15,000 miles)","More than 25,000 km (15,000 miles)"},
            {"Never","Occasionally (1-2 times/week)","Frequently (3-4 times/week)","Always (5+ times/week)"},
            {"Under 1 hour","1-3 hours","3-5 hours","5-10 hours","More than 10 hours"},
            {"None","1-2 flights","3-5 flights","6-10 flights","More than 10 flights"},
            {"None","1-2 flights","3-5 flights","6-10 flights","More than 10 flights"},
            {"Vegetarian","Vegan","Pescatarian (fish/seafood)","Meat-based (eat all types of animal products)"},
            {"Daily","Frequently (3-5 times/week)","Occasionally (1-2 times/week)","Never"},
            {"Daily","Frequently (3-5 times/week)","Occasionally (1-2 times/week)","Never"},
            {"Daily","Frequently (3-5 times/week)","Occasionally (1-2 times/week)","Never"},
            {"Daily","Frequently (3-5 times/week)","Occasionally (1-2 times/week)","Never"},
            {"Never","Rarely","Occasionally","Frequently"},
            {"Detached house","Semi-detached house","Townhouse","Condo/Apartment","Other"},
            {"1","2","3-4","5 0r more"},
            {"Under 1000 sq. ft","1000-2000 sq. ft","Over 2000 sq. ft"},
            {"Natural Gas","Electricity","Oil","Propane","Wood","Other"},
            {"Under $50","$50-$100","$100-$150","$150-$200","Over $200"},
            {"Natural Gas","Electricity","Oil","Propane","Wood","Other"},
            {"Yes, primarily (more than 50% of energy use)","Yes, partially (less than 50% of energy use)","No"},
            {"Monthly","Quarterly","Annually","Rarely"},
            {"Yes, regularly","Yes, occasionally","No"},
            {"None","1","2","3 or more"},
            {"Never","Occasionally","Frequently","Always"}
    };
    public  String getQuestion(int i) //gets the question
    {
        if( i<0)
            return questions[0];
        else if ( i> questions.length)
            return questions[questions.length-1];
        else
            return questions[i];
    }
    public  String[] getAnswerChoices(int i) // gets the answer choices
    {
        if( i<0)
            return answerChoices[0];
        else if ( i> answerChoices.length)
            return answerChoices[answerChoices.length-1];
        else
            return answerChoices[i];
    }

}
