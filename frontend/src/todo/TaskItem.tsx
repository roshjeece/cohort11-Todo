import React from 'react';
import type {Task} from "./TaskType.ts";

type TaskProps = {
    initialTask: Task
}

export const TaskItem = ({initialTask}: TaskProps) => {

    return (

            <li aria-label="Task 1">
                {`${initialTask.title}`}{`${initialTask.description}`}
            </li>

    );
};

export default TaskItem;