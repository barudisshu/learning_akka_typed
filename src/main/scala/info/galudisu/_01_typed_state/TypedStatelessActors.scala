package info.galudisu._01_typed_state

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorSystem, Behavior}

import java.util.concurrent.TimeUnit

object TypedStatelessActors extends App {

  sealed trait SimpleThing
  case object EatingChocolate extends SimpleThing
  case object WashDishes      extends SimpleThing
  case object LearnAkka       extends SimpleThing

  // stateful actor
  val emotionalMutableActor: Behavior[SimpleThing] = Behaviors.setup { context =>
    // spin up the actor state
    var happiness = 0

    // behavior of the actor
    Behaviors.receiveMessage {
      case EatingChocolate =>
        context.log.info(s"$happiness Eating Chocolate, getting a shot of dopamine!")
        happiness += 1
        Behaviors.same

      case WashDishes =>
        context.log.info(s"$happiness Doing a chore, womo, womo...")
        happiness -= 2
        Behaviors.same

      case LearnAkka =>
        context.log.info(s"$happiness Learning akka, that is cool")
        happiness += 100
        Behaviors.same

      case null =>
        context.log.info(s"$happiness Received something I don't know")
        Behaviors.same
    }

  }

  // Stateless actor
  def emotionalFunctionalActor(happiness: Int = 0): Behavior[SimpleThing] = Behaviors.receive { (context, message) =>
    message match {
      case EatingChocolate =>
        context.log.info(s"$happiness Eating Chocolate, getting a shot of dopamine!")
        emotionalFunctionalActor(happiness + 1)
      case WashDishes =>
        context.log.info(s"$happiness Doing a chore, womo, womo...")
        emotionalFunctionalActor(happiness - 2)
      case LearnAkka =>
        context.log.info(s"$happiness Learning akka, that is cool")
        emotionalFunctionalActor(happiness + 100)
      case null =>
        context.log.info(s"$happiness Received something I don't know")
        Behaviors.same
    }
  }

  val emotionalActorSystem = ActorSystem(emotionalFunctionalActor(), "EmotionalSystem")

  emotionalActorSystem ! EatingChocolate
  emotionalActorSystem ! EatingChocolate
  emotionalActorSystem ! EatingChocolate
  emotionalActorSystem ! WashDishes
  emotionalActorSystem ! LearnAkka

  TimeUnit.SECONDS.sleep(3)
  emotionalActorSystem.terminate()
}
