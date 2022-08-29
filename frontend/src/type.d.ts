declare interface ListGroup {
    id?: number,
    name: string
}

declare interface TaskList {
    id?: number,
    name: string,
    groupid: number
}

declare interface Task {
    id?: number,
    name: string,
    isdone: boolean,
    listid: number
}

declare interface FileItem {
    id?: number,
    name: string,
    path: string,
    taskid: number
}