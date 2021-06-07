import kotlinx.browser.window
import kotlinx.css.*
import kotlinx.html.FormMethod
import kotlinx.html.InputType
import kotlinx.html.hidden
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.onSubmitFunction
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLSelectElement
import org.w3c.xhr.XMLHttpRequest
import react.*
import react.dom.option
import styled.*


data class BookSearchState(
    var isSearchVisible: Boolean = false,
    var titleInput: String = "",
    var authorsInput: String = "",
    var typeInput: String = "",
    var ascDesc: Boolean = false,
    var sorting: String = "Alphabetic",
    var bookList: List<Items> = ArrayList()
) : RState

external interface BookSearchProps : RProps {

}

@JsExport
class BookSearch : RComponent<BookSearchProps, BookSearchState>() {

    override fun componentDidMount() {
        setState(
            BookSearchState()
        )
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
                        setState {
                            isSearchVisible = !isSearchVisible
                        }
                    }
                }
                +"Search"
            }

            styledForm {
                attrs {
                    method = FormMethod.post
                    id = "searchForm"
                    onSubmitFunction = {
                        val getRequest = XMLHttpRequest()
                        var queryString = "/items?ascDesc=${if (state.ascDesc) "ASC" else "DESC"}"
                        if (!state.titleInput.isBlank())
                            queryString += "&title=${state.titleInput}"
                        if (!state.authorsInput.isBlank())
                            queryString += "&authors=${state.authorsInput}"
                        if (!state.typeInput.isBlank())
                            queryString += "&type=${state.typeInput}"
                        getRequest.open("get", queryString)
                        getRequest.onload = {
                            setState { bookList = Json.decodeFromString(getRequest.responseText) }
                            console.log(getRequest.responseText)
                        }
                        getRequest.send()
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
                                    value = state.titleInput
                                    onChangeFunction = {
                                        val newValue = (it.target as HTMLInputElement).value
                                        setState { titleInput = newValue }
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
                                    value = state.authorsInput
                                    onChangeFunction = {
                                        val newValue = (it.target as HTMLInputElement).value
                                        setState { authorsInput = newValue }
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
                                        setState { typeInput = newValue }
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
                                        setState { sorting = newValue }
                                    }
                                }
                                option {
                                    attrs {
                                        value = "Alphabetic"
                                        selected = true
                                    }
                                    +"По наименованию"
                                }
//                                option {
//                                    attrs {
//                                        value = "ByDateReleased"
//                                    }
//                                    +"По дате выпуска"
//                                }
//                                option {
//                                    attrs {
//                                        value = "ByDateAdded"
//                                        selected = true
//                                    }
//                                    +"По дате добавления"
//                                }
                            }
                            styledB {
                                attrs {
                                    onClickFunction = {
                                        setState { ascDesc = !ascDesc }
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

            styledDiv {

            }
        }
    }
}


fun RBuilder.generalBody(): ReactElement {
    return child(BookSearch::class) {

    }
}