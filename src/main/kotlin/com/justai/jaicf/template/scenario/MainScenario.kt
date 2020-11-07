package com.justai.jaicf.template.scenario

import com.justai.jaicf.activator.caila.CailaIntentActivator
import com.justai.jaicf.activator.caila.caila
import com.justai.jaicf.model.scenario.Scenario
import kotlinx.serialization.json.JsonObject
import java.net.URL
import java.nio.file.PathMatcher
import java.util.regex.Pattern

import kotlinx.serialization.*
import kotlinx.serialization.json.*


//@Serializable
//data class Project(val name: String, val language: String)
//
//fun main() {
//    // Serializing objects
////    val data = Project("kotlinx.serialization", "Kotlin")
////    val string = Json.encodeToString(data)
////    println(string) // {"name":"kotlinx.serialization","language":"Kotlin"}
//    // Deserializing back into objects
//    val obj = Json.decodeFromString<Project>(string)
//    println(obj) // Project(name=kotlinx.serialization, language=Kotlin)
//    Json.string
//}

object MainScenario : Scenario() {

    init {
        state("start") {
            activators {
//                catchAll()
//                event()
                regex("/start")
                intent("Hello")
            }
            action {
                reactions.run {
//                    image("https://media.giphy.com/media/ICOgUNjpvO0PC/source.gif")
                    sayRandom(
                        "Hey I'm Foody Fin! What do you want to know about this product?",
                        "Foody Finn reporting for duty! Questions about this product?"
                    )
                    buttons(
                        "How much cals are there?",
                        "How much fat?",
                        "What is your name again?"
                    )
                }
            }
        }

        state("bye") {
            activators {
                intent("Bye")
            }

            action {
                reactions.sayRandom(
                    "See you soon!",
                    "Bye-bye!",
                    "It was a pleasure!"
                )
                reactions.image("https://media.giphy.com/media/EE185t7OeMbTy/source.gif")
            }
        }

//        state("search") {
//            activators {
//                intent("search")
//            }
//
//            action {
//                var response = URL("https://google.com").readText()
//                reactions.run {
//                    say(response)
//                    image("https://media.giphy.com/media/ICOgUNjpvO0PC/source.gif")
//                    sayRandom(
//                        "Hope that helped!",
//                        "Did this answer your question?"
//                    )
//                    buttons(
//                        "Give me more suggestions!"
//                    )
//                }
//            }
//        }

        state("search_fat") {
            activators {
                intent("/search/fat")
                intent("fat")
            }

            action {
                var response = URL("https://www.google.com").readText()
                reactions.run {
                    say("Looking up how much fats there are!")
                    say(response)
                    sayRandom(
                        "Hope that helped!",
                        "Did this answer your question?"
                    )
                    buttons(
                        "What are the calories?"
                    )
                }
            }
        }

        state("search_calories") {
            activators {
//                regex("calories")
                intent("/search/calories")
                intent("calories")
            }

            action {
                var barcode = "737628064502"
                var response = URL("https://world.openfoodfacts.org/api/v0/product/$barcode.json").readText()


//                val parser: Parser = Parser.default()()
//                val json: JsonObject = parser.parse(response) as JsonObject
//                println("Name : ${json.string("name")}, Age : ${json.int("age")}")

                print(response)



                reactions.run {
                    say("Looking up how much calories there are!")
                    say(response)
                    sayRandom(
                        "Hope that helped!",
                        "Did this answer your question?"
                    )
                    buttons(
                        "What are the fat values?"
                    )
                }
            }
        }

        // https://edcd8d22065c.ngrok.io/api/products/<bar_code> --> Miel endpoint

        // countries : list of countries where the product is sold


        // generic_name_en e.g. rice noodles --> "these rice noodles contain...'

//       ingredients_text_with_allergens (ingredients_ids_debug --> each separate in list)

       //allergens

        //  "additives_original_tags": [
        //      "en:e330"
        //    ],

        // labels = gluten free



        // serving size (serving_size_imported)

        // nutrient levels --> SUmmary
//        "nutrient_levels": {
//            "fat": "moderate",
//            "salt": "moderate",
//            "sugars": "high",
//            "saturated-fat": "moderate"
//        },


//        state("search", noContext = true) {
//            activators {
//                intent("search")
//            }
//
//            action {
//                activator.caila?.topIntent?.answer?.let {
//                    reactions.say("Search is called.")
//                    reactions.say(it)
//                }
//            }
//        }



        state("smalltalk", noContext = true) {
            activators {
                anyIntent()
            }

            action {
                activator.caila?.topIntent?.answer?.let {
                    reactions.say(it)
                }
            }
        }



        fallback {
            reactions.sayRandom(
                "Sorry, I didn't get that...",
                "Sorry, could you repeat please?"
            )
        }
    }
}