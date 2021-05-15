import kotlinx.css.CSSBuilder
import kotlinx.css.body
import kotlinx.css.margin
import kotlinx.css.px
import styled.StyleSheet
import styled.rawStyled

object GlobalStyles : StyleSheet("GlobalStyles", isStatic = true) {
    private val styles = CSSBuilder().apply {
        body {
            margin(0.px)
        }
    }

    fun applyGlobalStyle() {
        styled.injectGlobal(styles.toString())
    }
}