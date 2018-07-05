import org.w3c.dom.events.Event
import react.RProps

class SimpleProps<T>(var value: T?,
                     var onClickFunction: (Event) -> Unit) : RProps