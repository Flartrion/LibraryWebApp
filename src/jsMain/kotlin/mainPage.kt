import adminPageRelated.adminPage
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.css.*
import kotlinx.html.LINK
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import styled.css
import styled.styledDiv


external interface MainPageProps : RProps {
    var location: SiteLocation
}


data class LocationState(val location: SiteLocation) : RState

@JsExport
class MainPage : RComponent<MainPageProps, LocationState>() {

    init {
        state = LocationState(SiteLocation.Main)
//        setState(LocationState(SiteLocation.Main))
    }

    override fun RBuilder.render() {

        styledDiv {
            header {
                onChangeLocation = {
                    setState(LocationState(it))
                }
            }

            styledDiv {
                css {
                    display = Display.flex
                    flexDirection = FlexDirection.row
                }
                loginMenu() {
                    onRegisterPressed = {
                        setState(LocationState(SiteLocation.Register))
                    }
                }


                when (state.location) {
                    SiteLocation.Main -> {
                        generalBody()
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
                        padding(30.px)
                        display = Display.inlineBlock
                        paddingLeft = 10.px
                        paddingRight = 10.px
                        backgroundColor = Color.darkRed
                        width = 170.px
//                height = LinearDimension.maxContent
                    }
                }
            }


            footer {
                onChangeLocation = {
                    setState(LocationState(it))
                }
            }
        }
    }
}
