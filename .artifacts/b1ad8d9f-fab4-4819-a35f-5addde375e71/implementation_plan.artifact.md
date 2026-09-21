# Fix Task Editing and Add Description Validation

The user reported two issues:
1.  **Form Initialization:** When editing an existing task, the title and description are not prepopulated because the `remember`ed state is initialized before the task list is loaded from Firestore.
2.  **Missing Validation:** The app allows creating or updating tasks with an empty description, which should be prevented.

## Proposed Changes

### UI Layer

#### [MODIFY] [TaskFormScreen.kt](file:///C:/Users/Juan%20Diego%20Cardona/AndroidStudioProjects/CRUD_Firebase/app/src/main/java/com/example/crud_firebase/ui/screen/taskform/TaskFormScreen.kt)
- Add a `LaunchedEffect(existingTask)` to populate the `title` and `description` states when `existingTask` changes from `null` to a valid task.
- Update the `Button`'s `enabled` condition to include `description.isNotBlank()`.
- (Optional but good) Add `error` state to `OutlinedTextField` for description if it's empty and the user has interacted with it.

### Domain Layer

#### [MODIFY] [CreateTaskUseCase.kt](file:///C:/Users/Juan%20Diego%20Cardona/AndroidStudioProjects/CRUD_Firebase/app/src/main/java/com/example/crud_firebase/domain/usecase/task/CreateTaskUseCase.kt)
- Add validation to ensure `task.description.isNotBlank()`.

#### [MODIFY] [UpdateTaskUseCase.kt](file:///C:/Users/Juan%20Diego%20Cardona/AndroidStudioProjects/CRUD_Firebase/app/src/main/java/com/example/crud_firebase/domain/usecase/task/UpdateTaskUseCase.kt)
- Add validation to ensure `task.description.isNotBlank()`.

### ViewModel Layer

#### [MODIFY] [TaskViewModel.kt](file:///C:/Users/Juan%20Diego%20Cardona/AndroidStudioProjects/CRUD_Firebase/app/src/main/java/com/example/crud_firebase/ui/TaskViewModel.kt)
- Update `addTask` to check for empty description.

## Verification Plan

### Manual Verification
- Open the app and navigate to "Edit Tarea" for an existing task. Verify that the title and description are correctly loaded into the fields.
- Try to create a new task with an empty description. Verify that the "Publicar Tarea" button is disabled.
- Try to edit a task and clear the description. Verify that the "Guardar Cambios" button is disabled.
