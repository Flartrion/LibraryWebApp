import adminPageRelated.adminPage
import itemRelated.bookSearch
import kotlinx.browser.document
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.useEffect
import react.useState


fun MainPage = FC<Props> {
    var location: SiteLocation? by useState(SiteLocation.Main)
    var loggedRole: String? by useState()

    useEffect {
        val role = document.cookie.substringAfter("role=", "").substringBefore(";")
        loggedRole = if (role != undefined)
            ""
        else
            role
    }
    div {
//        css {
//            input {
//                borderRadius = 0.px
//                borderStyle = BorderStyle.none
//            }
//            button {
//                borderRadius = 0.px
//                borderStyle = BorderStyle.none
//                color = Color.white
//                backgroundColor = Color("#999999")
//                hover {
//                    backgroundColor = Color("#aaaaaa")
//                    color = Color.darkRed
//                }
//                disabled {
//                    backgroundColor = Color("#aaaaaa")
//                    color = Color.white
//                }
//            }
//        }

        header {
            onChangeLocation = {
                location = it
            }
        }

        div {
//            css {
//                display = Display.flex
//                flexDirection = FlexDirection.row
//            }
            loginMenu {
                onLogin = {
                    loggedRole = document.cookie.substringAfter("role=").substringBefore(";")
                }
                onExit = {
                    location = if (SiteLocation.AdminPanel == location) SiteLocation.Main else location
                }
            }


            when (location) {
                SiteLocation.Main -> {
                    bookSearch {
                        withRole = loggedRole != undefined
                    }
                    document.title = "Библиотека книг"
                }

                SiteLocation.Contacts -> {
                    contactsPage()
                    document.title = "Контакты библиотеки"
                }

                SiteLocation.Schedule -> {
                    schedulePage()
                    document.title = "Читателям библиотеки"
                }

                SiteLocation.About -> {
                    aboutPage()
                    document.title = "О библиотеке"
                }

                SiteLocation.AdminPanel -> {
                    adminPage()
                    document.title = "AWAKEN, MY MASTERS!"
                }

                SiteLocation.Register -> {
                    registerPage()
                    document.title = "HOHO, MUKATTE KURU NO KA?"
                }

                else -> {
                    h1 {
                        "Something obviously went very wrong, mate"
                    }
                }
            }

            div {
//                css {
//                    display = Display.inlineBlock
//                    backgroundColor = Color.darkRed
//                    width = LinearDimension("10%")
////                height = LinearDimension.maxContent
//                }
            }
        }


        footer {
            onChangeLocation = {
                location = it
            }
        }
    }
}

