package com.justai.jaicf.template.scenario

import com.justai.jaicf.activator.caila.caila
import com.justai.jaicf.model.scenario.Scenario
import java.net.URL

import com.beust.klaxon.Klaxon
import com.justai.jaicf.channel.jaicp.dto.chatwidget
import java.io.StringReader
import java.util.*

//https://github.com/just-ai/jaicf-kotlin/wiki/context
object MainScenario : Scenario() {

    class Product(val product: Dictionary<String,String>)

    init {
//        data class Person(
//            @Json(name = "the_name")
//            val name: String
//        )
//        val result = Klaxon()
//            .parse<Person>("""
//    {
//      "the_name": "John Smith", // note the field name
//      "age": 23
//    }
//""")

//        assert(result.name == "John Smith")
//        assert(result.age == 23)
//        bind("preProcess", function(ctx){
//            var $session = ctx.session;
//            var $parseTree = ctx.parseTree;
//            $session.start = $parseTree.text.substring($parseTree.text.lastIndexOf("start") + "start ".length);
//            if($session.start) {
//            try {
//                $session.start = JSON.parse($session.start);
//            }  catch(e) {
//                log("start data non JSON object");
//            }
//        }
//        });

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
                        "How much cals are in there?",
                        "Produced where?",
                        "Alternative products?"
                    )

                    var barcode = request.chatwidget?.jaicp?.data

                    context.client["barcode"] = barcode
                    reactions.say("Your barcode is ${barcode}")

//                    var barcode = "737628064502"
//                var barcode = context.client["barcode"]
                    var response = URL("https://world.openfoodfacts.org/api/v0/product/${barcode}.json").readText()

                    val klaxon = Klaxon()
                    val parsed = klaxon.parseJsonObject(StringReader(response))
                    val dataArray = parsed.array<Any>("product")
                    val productObj = dataArray?.let { klaxon.parseFromJsonArray<Product>(it) }
                    println(productObj)

                    context.client["product"] = productObj

//                val parser: Parser = Parser.default()()
//                val json: JsonObject = parser.parse(response) as JsonObject
//                println("Name : ${json.string("name")}, Age : ${json.int("age")}")

//                    print(response)
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
                        "What are the calories?",
                        "Where does my food come frome?"
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

                var response = context.client["data"]


                reactions.run {
                    say("Looking up how much calories there are!")
//                    say(response)
                    sayRandom(
                        "Hope that helped!",
                        "Did this answer your question?"
                    )
                    buttons(
                        "What are the fat values?",
                        "Where does my food come frome?"
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