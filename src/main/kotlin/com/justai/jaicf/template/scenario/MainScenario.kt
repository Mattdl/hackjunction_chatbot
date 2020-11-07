package com.justai.jaicf.template.scenario

import com.justai.jaicf.activator.caila.CailaIntentActivator
import com.justai.jaicf.activator.caila.caila
import com.justai.jaicf.model.scenario.Scenario

import java.net.URL

object MainScenario : Scenario() {

    init {
        state("start") {
            activators {
                catchAll()
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
                var response = URL("https://www.google.com/search?sxsrf=ALeKk00QDWc6WCDnOrhh_mz12cH3cwR0zg%3A1604751362992&source=hp&ei=ApCmX9q1OdLisAfqyJOIAg&q=fat&oq=fat&gs_lcp=CgZwc3ktYWIQAzIICC4QsQMQkwIyBQgAELEDMgIIADIICAAQsQMQgwEyAggAMgUIABCxAzIFCC4QsQMyBQguELEDMgIIADIFCAAQsQM6BAgjECc6CggAELEDEIMBEEM6BwgAELEDEEM6AgguUMkDWMYGYMIHaABwAHgAgAFhiAGZApIBATOYAQCgAQGqAQdnd3Mtd2l6&sclient=psy-ab&ved=0ahUKEwjavsPXtPDsAhVSMewKHWrkBCEQ4dUDCAc&uact=5").readText()
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
                var response = URL("https://www.google.com").readText()
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