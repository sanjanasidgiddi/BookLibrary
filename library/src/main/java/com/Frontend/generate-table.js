function generateTable(header, objs){
    let table = document.getElementById("book_list_id")

    let tbody = document.createElement("tbody")

    let tBodyRow = document.createElement("tr")

    for (let key of header){
        let th = document.createElement("th")
        th.innerHTML = key

        tBodyRow.appendChild(th)
    }
    tbody.appendChild(tBodyRow)

    table.appendChild(tbody)

    for (let obj of objs){
        let tr = document.createElement("tr")

        console.log(obj)
        for (let key in obj){
            let td = document.createElement("td")

            td.innerHTML = `${obj[key]}`

            tr.appendChild(td)
        }

        table.appendChild(tr)
    }
}