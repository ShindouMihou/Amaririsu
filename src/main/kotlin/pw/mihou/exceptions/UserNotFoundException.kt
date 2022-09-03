package pw.mihou.exceptions

class UserNotFoundException(link: String):
    RuntimeException("The user ($link) cannot be accessed because there is no user linked with the link.")