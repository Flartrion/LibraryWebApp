import kotlinx.browser.window
import kotlinx.css.*
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import styled.css
import styled.styledDiv

class RegisterPage: RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                textAlign = TextAlign.center
                width = LinearDimension.fillAvailable
                minHeight = (window.innerHeight - 200).px
                height = LinearDimension.maxContent

                padding(30.px)
                backgroundColor = Color("#aaaaaa")
                display = Display.inlineBlock
                fontFamily = "Arial"
            }

        }
    }
}

fun RBuilder.registerPage() {
    child(RegisterPage::class) {

    }
}