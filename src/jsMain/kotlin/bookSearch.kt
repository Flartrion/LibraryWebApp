import kotlinx.browser.window
import kotlinx.css.*
import kotlinx.html.*
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.onSubmitFunction
import org.w3c.dom.HTMLFormElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLSelectElement
import org.w3c.xhr.FormData
import org.w3c.xhr.XMLHttpRequest
import react.*
import react.dom.option
import styled.*


data class BookSearchState(
    val isSearchVisible: Boolean,
    val nameInput: String = "",
    val authorInput: String = "",
    val typeInput: String = "",
    val ascDesc: Boolean,
    val sorting: String
) : RState

external interface BookSearchProps : RProps {

}

@JsExport
class BookSearch : RComponent<BookSearchProps, BookSearchState>() {

    override fun componentDidMount() {
        setState(BookSearchState(isSearchVisible = false, ascDesc = false, sorting = "ByDateAdded"))
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
                        setState(
                            BookSearchState(
                                !state.isSearchVisible,
                                state.nameInput,
                                state.authorInput,
                                state.typeInput,
                                state.ascDesc,
                                state.sorting
                            )
                        )
                    }
                }
                +"Search"
            }

            styledForm {
                attrs {
                    action = "/search"
                    method = FormMethod.post
                    id = "searchForm"
//                    target = "_blank"
                    onSubmitFunction = {
                        val xxx = it.target as HTMLFormElement
                        console.log(xxx.elements)
                        val sss = XMLHttpRequest()
                        it.preventDefault()
                    }
                }
                styledTable {
                    attrs {
                        hidden = !state.isSearchVisible
                    }
                    css {
                        width = LinearDimension.fillAvailable
                        backgroundColor = Color("#888888")
                        children {
                            children {
                                textAlign = TextAlign.left
                                firstChild {
                                    textAlign = TextAlign.right
                                }
                                input {
                                    width = 500.px
                                }
                            }
                            lastChild {
                                children {
                                    textAlign = TextAlign.center
                                }
                            }
                        }
                    }
                    styledColGroup {
                        styledCol {
                            css {
                                width = LinearDimension("30%")
                                textAlign = TextAlign.right
                            }
                        }
                        styledCol {
                            css {
                                textAlign = TextAlign.left
                            }
                        }
                    }

                    styledTr {
                        styledTd {
                            styledLabel { +"Заглавие: " }
                        }
                        styledTd {
                            styledInput {
                                attrs {
                                    form = "searchForm"
                                    name = "name"
                                    type = InputType.text
                                    value = state.nameInput
                                    onChangeFunction = {
                                        val newValue = (it.target as HTMLInputElement).value
//                                        setState(state.copy(nameInput = value))
                                        setState(
                                            BookSearchState(
                                                state.isSearchVisible,
                                                newValue,
                                                state.authorInput,
                                                state.typeInput,
                                                state.ascDesc,
                                                state.sorting
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                    styledTr {
                        styledTd {
                            styledLabel { +"Авторы: " }
                        }
                        styledTd {
                            styledInput {
                                attrs {
                                    form = "searchForm"
                                    name = "authors"
                                    type = InputType.text
                                    value = state.authorInput
                                    onChangeFunction = {
                                        val newValue = (it.target as HTMLInputElement).value
                                        setState(
                                            BookSearchState(
                                                state.isSearchVisible,
                                                state.nameInput,
                                                newValue,
                                                state.typeInput,
                                                state.ascDesc,
                                                state.sorting
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }

                    styledTr {
                        styledTd {
                            styledLabel { +"Тип: " }
                        }
                        styledTd {
                            styledInput {
                                attrs {
                                    form = "searchForm"
                                    name = "type"
                                    value = state.typeInput
                                    onChangeFunction = {
                                        val newValue = (it.target as HTMLInputElement).value
                                        setState(
                                            BookSearchState(
                                                state.isSearchVisible,
                                                state.nameInput,
                                                state.authorInput,
                                                newValue,
                                                state.ascDesc,
                                                state.sorting
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }

                    styledTr {
                        styledTd {
                            styledLabel { +"Сортировать: " }
                        }
                        styledTd {
                            css {
                                select {
                                    width = LinearDimension("200px")
                                }
                                b {
                                    userSelect = UserSelect.none
                                    padding(left = 5.px, right = 5.px, bottom = 2.px)
                                    borderStyle = BorderStyle.solid
                                    borderWidth = 1.px
                                    backgroundColor = Color("#999999")
                                    color = Color.white
                                    hover {
                                        backgroundColor = Color("#cccccc")
                                        color = Color.darkRed
                                    }
                                }
                            }
                            styledSelect {
                                attrs {
                                    form = "searchForm"
                                    name = "sorting"
                                    value = state.sorting
                                    onChangeFunction = {
                                        val newValue = (it.target as HTMLSelectElement).value
                                        setState(
                                            BookSearchState(
                                                state.isSearchVisible,
                                                state.nameInput,
                                                state.authorInput,
                                                state.typeInput,
                                                state.ascDesc,
                                                newValue
                                            )
                                        )
                                    }
                                }
                                option {
                                    attrs {
                                        value = "Alphabetic"
                                    }
                                    +"По наименованию"
                                }
                                option {
                                    attrs {
                                        value = "ByDateReleased"
                                    }
                                    +"По дате выпуска"
                                }
                                option {
                                    attrs {
                                        value = "ByDateAdded"
                                        selected = true
                                    }
                                    +"По дате добавления"
                                }
                            }
                            styledInput {
                                attrs {
                                    form = "searchForm"
                                    name = "ascDesc"
                                    hidden = true
                                    type = InputType.checkBox
                                    checked = state.ascDesc
                                }
                            }
                            styledB {
                                attrs {
                                    onClickFunction = {
                                        setState(
                                            BookSearchState(
                                                true,
                                                state.nameInput,
                                                state.authorInput,
                                                state.typeInput,
                                                !state.ascDesc,
                                                state.sorting
                                            )
                                        )
                                    }
                                }
                                if (state.ascDesc)
                                    +"↑" else
                                    +"↓"
                            }
                        }
                    }

                    styledTr {
                        styledTd {
                            attrs {
                                colSpan = "2"
                            }
                            styledInput {
                                attrs {
                                    form = "searchForm"
                                    name = "submit"
                                    value = "Let's goooo"
                                    type = InputType.submit
//                                onClickFunction = {
//                                    val request = XMLHttpRequest()
//                                    request.open("post", "/")
//                                    request.addEventListener("load", {
//
//                                    })
//                                    request.send()
//                                }
                                }
                                css {
                                    borderRadius = 0.px
                                    fontFamily = "Arial"
                                    borderStyle = BorderStyle.none
                                    backgroundColor = Color("#999999")
                                    color = Color.white
                                    hover {
                                        backgroundColor = Color("#cccccc")
                                        color = Color.darkRed
                                    }
                                }
                            }
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