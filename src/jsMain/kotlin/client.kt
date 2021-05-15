import react.dom.render
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.css.StyledElement
import kotlinx.css.body
import styled.Styled
import styled.StyledDOMBuilder
import styled.css
import styled.styled


fun main() {
    GlobalStyles.applyGlobalStyle()
    window.onload = {
        render(document.getElementById("root")) {
            child(MainPage::class) {
            }
        }

    }
}
