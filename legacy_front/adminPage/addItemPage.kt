package adminPage

import emotion.react.css
import react.FC
import react.Props
import react.dom.html.ReactHTML.col
import react.dom.html.ReactHTML.colgroup
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
import react.dom.html.ReactHTML.table
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.textarea
import react.dom.html.ReactHTML.tr
import react.useState
import web.cssom.*
import web.html.InputType


external interface AddItemPageProps : Props {
    var onCompleteFunction: () -> Unit
}


val AddItemPage = FC<AddItemPageProps> { props ->
    var isbninput: String by useState("")
    var rlbcinput: String by useState("")
    var titleinput: String by useState("")
    var authorsinput: String by useState("")
    var typeinput: String by useState("")
    var detailsinput: String by useState("")
    var languageinput: String by useState("")

    table {
        css {
            width = 0.3.pc
            textAlign = TextAlign.left
            firstChild {
                textAlign = TextAlign.right
            }
            input {
                width = 500.px
            }

            lastChild {
                textAlign = TextAlign.center
            }
        }
        colgroup {
            col {
                css {
                    width = 0.3.pc
                    textAlign = TextAlign.right
                }
            }
            col {
                css {
                    textAlign = TextAlign.left
                }
            }
        }

        tr {
            td {
                label { +"ISBN: " }
            }
            td {
                input {
                    onChange = {
                        isbninput = it.target.value
                    }
                    type = InputType.text
                }
            }
        }

        tr {
            td {
                label { +"ББК: " }
            }
            td {
                input {
                    onChange = {
                        rlbcinput = it.target.value
                    }
                    type = InputType.text

                }
            }
        }

        tr {
            td {
                label { +"Название: " }
            }
            td {
                input {
                    onChange = {
                        titleinput = it.target.value
                    }
                    type = InputType.text
                }
            }
        }

        tr {
            td {
                label { +"Авторы: " }
            }
            td {
                input {
                    onChange = {
                        authorsinput = it.target.value
                    }
                    type = InputType.text
                }
            }

        }

        tr {
            td {
                label { +"Тип: " }
            }
            td {
                input {
                    onChange = {
                        typeinput = it.target.value
                    }
                    type = InputType.text

                }
            }
        }

        tr {
            td {
                label { +"Язык: " }
            }
            td {
                input {
                    onChange = {
                        languageinput = it.target.value
                    }
                    type = InputType.text

                }
            }
        }

        tr {
            td {
                label { +"Дополнительно: " }
            }
            td {
                textarea {
                    onChange = {
                        detailsinput = it.target.value
                    }
                }

            }
        }

        tr {
            td {
                colSpan = 2

                input {
                    value = "Подтвердить"
                    type = InputType.submit
                    onClick = {
                        props.onCompleteFunction()
                        TODO("Transfer this logic to proper controller")
//                        val request = XMLHttpRequest()
//                        request.open("post", "/items/insert")
//                        request.onload = {
//                            if (request.status == 200.toShort()) {
//                                props.onCompleteFunction()
//                            } else {
//                                window.alert("Something went wrong!\nReason: ${request.response}")
//                            }
//                        }
//                        val data = FormData()
//                        if (titleinput.isNotBlank())
//                            data.append("title", titleinput)
//                        if (authorsinput.isNotBlank())
//                            data.append("authors", authorsinput)
//                        if (typeinput.isNotBlank())
//                            data.append("type", typeinput)
//                        if (isbninput.isNotBlank())
//                            data.append("isbn", isbninput)
//                        if (rlbcinput.isNotBlank())
//                            data.append("rlbc", rlbcinput)
//                        if (detailsinput.isNotBlank())
//                            data.append("details", detailsinput)
//                        if (languageinput.isNotBlank())
//                            data.append("language", languageinput)
//                        request.send(data)
                    }

                    css {
                        font = Font(FontStyle.normal, FontSize.medium, FontFamily.serif)
                        backgroundColor = Color("#999999")
                        color = Color("#ffffff")
                        hover {
                            backgroundColor = Color("#cccccc")
                            color = Color("#880000")
                        }
                    }
                }
            }
        }
    }
}

