import adminPageRelated.adminPage
import itemRelated.bookSearch
import kotlinx.browser.document
import kotlinx.css.*
import react.*
import styled.css
import styled.styledDiv


external interface MainPageProps : RProps {
}


external interface MainPageState : RState {
    var location: SiteLocation
    var loggedRole: String
}

@JsExport
class MainPage : RComponent<MainPageProps, MainPageState>() {

    override fun componentWillMount() {
        setState {
            location = SiteLocation.Main
            loggedRole = ""
        }
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                input {
                    borderRadius = 0.px
                    borderStyle = BorderStyle.none
                }
                button {
                    borderRadius = 0.px
                    borderStyle = BorderStyle.none
                    color = Color.white
                    backgroundColor = Color("#999999")
                    hover {
                        backgroundColor = Color("#aaaaaa")
                        color = Color.darkRed
                    }
                    disabled {
                        backgroundColor = Color("#aaaaaa")
                        color = Color.white
                    }
                }
            }

            header {
                onChangeLocation = {
                    setState { location = it }
                }
            }

            styledDiv {
                css {
                    display = Display.flex
                    flexDirection = FlexDirection.row
                }
                loginMenu {
                    onLogin = {
                        setState { loggedRole = document.cookie }
                    }
                    onExit = {
                        setState { location = if (SiteLocation.AdminPanel == location) SiteLocation.Main else location }
                    }
                }


                when (state.location) {
                    SiteLocation.Main -> {
                        bookSearch()
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
                }

                styledDiv {
                    css {
                        display = Display.inlineBlock
                        backgroundColor = Color.darkRed
                        width = LinearDimension("10%")
//                height = LinearDimension.maxContent
                    }
                }
            }


            footer {
                onChangeLocation = {
                    setState { location = it }
                }
            }
        }
    }
}
