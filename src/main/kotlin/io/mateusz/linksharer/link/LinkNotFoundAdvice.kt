package io.mateusz.linksharer.link

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class LinkNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(LinkNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun linksContainerNotFoundHandler(ex: LinkNotFoundException): String {
        return ex.message.toString()
    }
}