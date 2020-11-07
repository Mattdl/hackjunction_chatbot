package com.justai.jaicf.template.scenario

import com.beust.klaxon.JsonObject
import com.justai.jaicf.activator.caila.caila
import com.justai.jaicf.model.scenario.Scenario
import java.net.URL

import com.beust.klaxon.Klaxon
import com.beust.klaxon.Parser
import com.justai.jaicf.channel.jaicp.dto.chatwidget
import java.io.StringReader
import java.util.*

//https://github.com/just-ai/jaicf-kotlin/wiki/context
object MainScenario : Scenario() {

    class MainJson(val product: JsonObject)
    class Product(val nutriments: Dictionary<String,Any>, val nutrient_levels: Dictionary<String,String>)

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

//                    var barcode = request.chatwidget?.jaicp?.data!!.jsonObject["JustWidgetRawParams"]
//                    context.client["barcode"] = barcode



//                    var barcode = script_context?.JustWidgetRawParams
//                    ["JustWidgetRawParams"]
//                    reactions.say("Your barcode is ${context.client["barcode"]}")
//                    reactions.say("Your barcode raw is ${barcode!!.jsonObject["barcode"]}")
//                    Your barcode is {"livechatStatus":{"enabled":false},"JustWidgetRawParams":{"barcode":"4001724004073"},"isTestChannel":false}

                    val barcode = "737628064502" //tmp
                    val response = URL("https://world.openfoodfacts.org/api/v0/product/${barcode}.json").readText()
//                    reactions.say("Response is ${response}")

                    val parser: Parser = Parser()
                    val stringBuilder: StringBuilder = StringBuilder(response)
                    val json: JsonObject = parser.parse(stringBuilder) as JsonObject

                    println(json.obj("product")?.string("product_name"))
                    val fat = println(json.obj("product")?.obj("nutriments")?.double("fat_100g"))
                    val cals = println(json.obj("product")?.obj("nutriments")?.double("energy-kcal_100g"))
                    val fat_sat = println(json.obj("product")?.obj("nutriments")?.double("saturated-fat_100g"))
                    val fat_trans = println(json.obj("product")?.obj("nutriments")?.double("trans-fat_100g"))
                    val sugar = println(json.obj("product")?.obj("nutriments")?.double("sugars_100g"))
                    val proteins = println(json.obj("product")?.obj("nutriments")?.double("proteins_100g"))

                    val fatlevel = println(json.obj("product")?.obj("nutrient_levels")?.string("nutrient_levels"))

                    // origins_old:
                    val origin = println(json.obj("product")?.string("origins_old"))



                    context.client["fat"] = fat
                    context.client["cals"] = cals
                    context.client["fat_sat"] = fat_sat
                    context.client["fat_trans"] = fat_trans
                    context.client["sugar"] = sugar
                    context.client["proteins"] = proteins
                    context.client["fatlevel"] = fatlevel
                    context.client["origin"] = origin
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
//                val json = context.client["json"].
//                val product = json. ["product"]

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