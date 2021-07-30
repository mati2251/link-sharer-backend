package io.mateusz.linksharer.linkscontainer

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class LinksContainerNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(LinksContainerNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun linksContainerNotFoundHandler(ex: LinksContainerNotFoundException): String {
        return ex.message.toString()
    }
}