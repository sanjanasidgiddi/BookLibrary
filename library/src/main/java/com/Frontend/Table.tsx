


export function Table(header: string[], objs: any[]){
    let list = []

    for (let key of header){
        list.push(
            <th>{key}</th>
        )
    }

    return (
        <table id="book-list-id">
            <tbody>
            <tr>
                {list}
            </tr>
            </tbody>
        </table>
    )

}