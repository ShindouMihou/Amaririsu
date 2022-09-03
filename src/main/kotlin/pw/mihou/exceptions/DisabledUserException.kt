package pw.mihou.exceptions

class DisabledUserException(link: String):
    RuntimeException("The user ($link) cannot be accessed because they have disabled their profile.")