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



//                    println(json.obj("product")?.string("product_name"))
//                    val sugar = println(json.obj("product")?.obj("nutriments")?.double("sugars_100g"))
//                    val proteins = println(json.obj("product")?.obj("nutriments")?.double("proteins_100g"))
//                    // origins_old:
//                    val origin = println(json.obj("product")?.string("origins_old"))



//                    context.client["fat"] = fat
//                    context.client["cals"] = cals
//                    context.client["fat_sat"] = fat_sat
//                    context.client["fat_trans"] = fat_trans
//                    context.client["sugar"] = sugar
//                    context.client["proteins"] = proteins
//                    context.client["fatlevel"] = fatlevel
//                    context.client["origin"] = origin
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
//                var barcode = request.chatwidget?.jaicp?.data!!.jsonObject["JustWidgetRawParams"]!!.jsonObject["barcode"]
                val barcode = "737628064502" //tmp
                val response = URL("https://world.openfoodfacts.org/api/v0/product/${barcode}.json").readText()
                val parser: Parser = Parser()
                val stringBuilder: StringBuilder = StringBuilder(response)
                val json: JsonObject = parser.parse(stringBuilder) as JsonObject

                val fat = json.obj("product")?.obj("nutriments")?.double("fat_100g")
                val cals = json.obj("product")?.obj("nutriments")?.int("energy-kcal_100g")
                val fat_sat = json.obj("product")?.obj("nutriments")?.double("saturated-fat_100g")
                val fat_trans = json.obj("product")?.obj("nutriments")?.int("trans-fat_100g")
                val fatlevel = json.obj("product")?.obj("nutrient_levels")?.string("nutrient_levels")

                reactions.run {
                    say("Per 100g , you have ${cals.toString()} kcal and ${fat.toString()}g fats from which ${fat_sat.toString()}g saturated." +
                            "This is considered a ${fatlevel} fat-level.")
//                    sayRandom(
//                        "Hope that helped!",
//                        "Did this answer your question?"
//                    )
                    buttons(
                        "What are the proteins?",
                        "What's the origin of the product?"
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
//                var barcode = request.chatwidget?.jaicp?.data!!.jsonObject["JustWidgetRawParams"]!!.jsonObject["barcode"]
                val barcode = "737628064502" //tmp
                val response = URL("https://world.openfoodfacts.org/api/v0/product/${barcode}.json").readText()
                val parser: Parser = Parser()
                val stringBuilder: StringBuilder = StringBuilder(response)
                val json: JsonObject = parser.parse(stringBuilder) as JsonObject

                val cals = json.obj("product")?.obj("nutriments")?.int("energy-kcal_100g")

                reactions.run {
                    say("Per 100g , you have ${cals.toString()} kcal of energy.")

                    buttons(
                        "What are the fat values?",
                        "Where does my food come from?"
                    )
                }
            }
        }

        state("search_sugar") {
            activators {
//                regex("calories")
                intent("/search/sugars")
                intent("sugars")
            }

            action {
//                var barcode = request.chatwidget?.jaicp?.data!!.jsonObject["JustWidgetRawParams"]!!.jsonObject["barcode"]
                val barcode = "737628064502" //tmp
                val response = URL("https://world.openfoodfacts.org/api/v0/product/${barcode}.json").readText()
                val parser: Parser = Parser()
                val stringBuilder: StringBuilder = StringBuilder(response)
                val json: JsonObject = parser.parse(stringBuilder) as JsonObject

                val sugar = json.obj("product")?.obj("nutriments")?.double("sugars_100g")

                reactions.run {
                    say("Per 100g , you have ${sugar.toString()}g of sugar.")

                    buttons(
                        "What are the fat values?",
                        "How much proteins?"
                    )
                }
            }
        }


        state("search_protein") {
            activators {
//                regex("calories")
                intent("/search/protein")
                intent("protein")
            }

            action {
//                var barcode = request.chatwidget?.jaicp?.data!!.jsonObject["JustWidgetRawParams"]!!.jsonObject["barcode"]
                val barcode = "737628064502" //tmp
                val response = URL("https://world.openfoodfacts.org/api/v0/product/${barcode}.json").readText()
                val parser: Parser = Parser()
                val stringBuilder: StringBuilder = StringBuilder(response)
                val json: JsonObject = parser.parse(stringBuilder) as JsonObject

                val proteins = json.obj("product")?.obj("nutriments")?.double("proteins_100g")

                reactions.run {
                    say("Per 100g, you have ${proteins.toString()}g of protein.")

                    buttons(
                        "What are the fat values?",
                        "What are the calories?"
                    )
                }
            }
        }

        state("origin") {
            activators {
//                regex("calories")
                intent("origin")
            }

            action {
//                var barcode = request.chatwidget?.jaicp?.data!!.jsonObject["JustWidgetRawParams"]!!.jsonObject["barcode"]
                val barcode = "737628064502" //tmp
                val response = URL("https://world.openfoodfacts.org/api/v0/product/${barcode}.json").readText()
                val parser: Parser = Parser()
                val stringBuilder: StringBuilder = StringBuilder(response)
                val json: JsonObject = parser.parse(stringBuilder) as JsonObject

                val origin = json.obj("product")?.string("origins_old")

                reactions.run {
                    say("This product was made in ${origin.toString()}.")

                    buttons(
                        "What are the fat values?",
                        "What are the calories?"
                    )
                }
            }
        }

        state("alternatives") {
            activators {
//                regex("calories")
                intent("alternatives")
            }

            action {
//                var barcode = request.chatwidget?.jaicp?.data!!.jsonObject["JustWidgetRawParams"]!!.jsonObject["barcode"]
                val barcode = "737628064502" //tmp
                val response = URL("https://world.openfoodfacts.org/api/v0/product/${barcode}.json").readText()
                val parser: Parser = Parser()
                val stringBuilder: StringBuilder = StringBuilder(response)
                val json: JsonObject = parser.parse(stringBuilder) as JsonObject

                val proteins = json.obj("product")?.obj("nutriments")?.double("proteins_100g")

                reactions.run {
                    say("Per 100g, you have ${proteins.toString()}g of protein.")

                    buttons(
                        "What are the fat values?",
                        "What are the calories?"
                    )
                }
            }
        }


        // Are there nuts in there
        state("nuts") {
            activators {
//                regex("calories")
                intent("nuts")
            }

            action {
//                var barcode = request.chatwidget?.jaicp?.data!!.jsonObject["JustWidgetRawParams"]!!.jsonObject["barcode"]
                val barcode = "737628064502" //tmp
                val response = URL("https://nutreat-backend.azurewebsites.net/api/products/${barcode}?planet=1&price=1&people=1").readText()
                val parser: Parser = Parser()
                val stringBuilder: StringBuilder = StringBuilder(response)
                val json: JsonObject = parser.parse(stringBuilder) as JsonObject

                val proteins = json.obj("product")?.string("ingredients_text_with_allergens_en")

                reactions.run {
                    say("Per 100g, you have ${proteins.toString()}g of protein.")

                    buttons(
                        "Can I suggest nut-free related products?"
                    )
                }
            }
        }


        state("alternatives") {
            activators {
//                regex("calories")
                intent("alternatives")
            }

            action {
//                var barcode = request.chatwidget?.jaicp?.data!!.jsonObject["JustWidgetRawParams"]!!.jsonObject["barcode"]
                val barcode = "737628064502" //tmp
                val response = URL("https://nutreat-backend.azurewebsites.net/api/products/${barcode}?planet=1&price=1&people=1").readText()
                val parser: Parser = Parser()
                val stringBuilder: StringBuilder = StringBuilder(response)
                val json: JsonObject = parser.parse(stringBuilder) as JsonObject

//                val proteins = json.obj("product")?.obj("nutriments")?.double("proteins_100g")

                reactions.run {
                    say("I would recommend the following alternative product [TODO].")

                    buttons(
                        "What are the fat values?",
                        "What are the calories?"
                    )
                }
            }
        }







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