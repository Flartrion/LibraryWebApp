import kotlinx.browser.window
import kotlinx.css.*
import kotlinx.css.properties.*
import kotlinx.html.InputType
import kotlinx.html.hidden
import kotlinx.html.js.onClickFunction
import react.*
import styled.*


data class BookSearchState(val isSearchVisible: Boolean) : RState {

}

@JsExport
class BookSearch : RComponent<RProps, BookSearchState>() {
    init {
        setState(BookSearchState(false))
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                textAlign = TextAlign.center
                width = LinearDimension.fillAvailable
                minHeight = (window.outerHeight - 200).px
                height = LinearDimension.maxContent

//                padding(30.px)
                backgroundColor = Color("#aaaaaa")
                display = Display.inlineBlock
                fontFamily = "Arial"
            }
            styledDiv {
                css {
                    padding(top = 10.px, bottom = 10.px)
                    backgroundColor = Color("#cccccc")
                    textAlign = TextAlign.center
                    cursor = Cursor.pointer
                    userSelect = UserSelect.none
                }
                attrs {
                    onClickFunction = {
                        setState(BookSearchState(!state.isSearchVisible))
                    }
                }
                +"Search"
            }
            styledDiv {
                attrs {
                    hidden = state.isSearchVisible
                }
                css {
                    overflow = Overflow.hidden
                    transition += Transition("height", Time("1s"), Timing.linear, Time("0s"))
                }
                styledForm {
                    styledInput {
                        attrs {
                            name = "ahaha"
                            type = InputType.text
                        }
                    }
                    styledInput {
                        attrs {
                            name = "ahaha2"
                            type = InputType.text
                        }
                    }
                    styledInput {
                        attrs {
                            name = "ahaha3"
                            type = InputType.text
                        }
                    }
                }
            }
        }
    }
}

fun RBuilder.generalBody(): ReactElement {
    return child(BookSearch::class) {

    }
}