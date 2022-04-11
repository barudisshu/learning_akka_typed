package info.galudisu._00_typed_incentives

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorSystem, Behavior}

object AkkaTypedIncentives {

  // 1 - typed messages & actors
  sealed trait ShoppingCartMessage
  case class AddItem(item: String)    extends ShoppingCartMessage
  case class RemoveItem(item: String) extends ShoppingCartMessage
  case object ValidateCard            extends ShoppingCartMessage

  val shoppingRootActor: ActorSystem[ShoppingCartMessage] = ActorSystem(
    Behaviors.receiveMessage[ShoppingCartMessage] { (message: ShoppingCartMessage) =>
      message match {
        case AddItem(item)    => println(s"Adding $item to cart")
        case RemoveItem(item) => println(s"Removing $item from cart")
        case ValidateCard =>
          println("The cart is good.")
      }
      Behaviors.same
    },
    "simpleShoppingActor"
  )

  shoppingRootActor ! ValidateCard

  // 2 - mutable state
  val shoppingRootActorMutable: ActorSystem[ShoppingCartMessage] = ActorSystem(
    Behaviors.setup[ShoppingCartMessage] { ctx =>

      // local state = mutable
      var items: Set[String] = Set()

      Behaviors.receiveMessage[ShoppingCartMessage] { (message: ShoppingCartMessage) =>
        message match {
          case AddItem(item) =>
            println(s"Adding $item to cart")
            // mutate variable
            items += item
          case RemoveItem(item) =>
            println(s"Removing $item from cart")
            items -= item
          case ValidateCard =>
            println("The cart is good.")
        }
        Behaviors.same
      }
    },
    "simpleShoppingActorMutable"
  )

  // avoid mutable behaviors
  def shoppingBehavior(items: Set[String]): Behavior[ShoppingCartMessage] =
    Behaviors.receiveMessage[ShoppingCartMessage] {
      case AddItem(item) =>
        println(s"Adding $item to cart")
        // mutate variable
        shoppingBehavior(items + item)
      case RemoveItem(item) =>
        println(s"Removing $item from cart")
        shoppingBehavior(items - item)
      case ValidateCard =>
        println("The cart is good.")
        Behaviors.same
    }

  // 3 - hierarchy
  // no more system.actorOf => using spawn
  val rootOnlineStoreActor: ActorSystem[Nothing] = ActorSystem(
    Behaviors.setup { ctx =>
      ctx.spawn(shoppingBehavior(Set()), "shoppingCart")

      Behaviors.empty
    },
    "onlineStore"
  )
}
