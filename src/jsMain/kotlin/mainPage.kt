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
}

@JsExport
class MainPage : RComponent<MainPageProps, MainPageState>() {

    override fun componentWillMount() {
        setState { location = SiteLocation.Main }
    }

    override fun RBuilder.render() {
        styledDiv {
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
                    onRegisterPressed = {
                        setState { location = SiteLocation.Register }
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
