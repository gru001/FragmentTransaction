# Demonstrate flavors of commit()

## commit() vs commitAllowingStateLoss()

  This app presents buttons, each of which will attempt to replace a big blue rectangle(WhiteRatFragment) with a big red rectangle(BlackRatFragment). The three different options are:

  * Don't allow state loss. This will crash the app because it will attempt to make the change after `onSaveInstanceState()`.
  * Allow state loss. This will make the change, but you may lose state (more on that in a second).
  * No state loss. This will make the change in a way that will not cause state loss.

  When allowing state loss, you can control whether or not state is lost using your device's developer options. If you turn on the "Don't Keep Activities" option, you will lose state after pressing the button.

  If you turn on "Don't Keep Activities" and press the "no state loss" button, you will see that the FragmentManager properly retains its state.

## commit(), commitNow(), and executePendingTransactions()

  The other variants of `commit()` specify when the transaction occurs. The documentation for `commit()` offers this explanation for timing:

  *Schedules a commit of this transaction. The commit does not happen immediately; it will be scheduled as work on the main thread to be done the next time that thread is ready.*
  
  What this means in practice is that you can perform any number of transactions at a time, and none of them will actually happen until the next time the main thread is ready. This includes adding, removing, and replacing Fragments in addition to popping the back stack via `popBackStack()`.
  
  Sometimes you want your transactions to happen immediately. Previously developers accomplished this by calling `executePendingTransactions()` after calling `commit()`. `executePendingTransactions()` will take all those transactions that you currently have queued up and will process them immediately.
  
  Version 24.0.0 of the support library added `commitNow()` as a better alternative to `executePendingTransactions()`. The former only executes the current transaction synchronously, whereas the latter will execute all of the transactions you have committed and are currently pending. `commitNow()` prevents you from accidentally executing more transactions than you actually want to execute.
  
  The caveat is that you can’t use `commitNow()` with a transaction that you are adding to the back stack. Think about it this way- if you were to add a transaction to the back stack via `commit()` then immediately add a transaction to the back stack via `commitNow()`, what does the back stack look like? Because the framework can’t provide any guarantees regarding the ordering here, it simply isn’t supported.
  
  On a side note, `popBackStack()` has a `popBackStackImmediate()` counterpart, which performs similarly to `commit()` and `commitNow()`. The former is asynchronous, the latter is synchronous.
  
  In app executing each operation sequentially after that immediately printing stack count.   
  
  
