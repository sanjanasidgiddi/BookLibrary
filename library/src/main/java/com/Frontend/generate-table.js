
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

var darkl_button = document.getElementById('darklight')

function toggleDarkLight(){
    /* Toggle Button Text */
    let current_text = darkl_button.innerText;
    darkl_button.innerText = current_text === 'Dark' ? 'Light' : 'Dark';
    /** Log it */
    console.log("Toggle button clicked!");
    /** Switch background and text colors using css class under wrapper */
    var theme_element = document.body;
    theme_element.classList.toggle("dark_mode");
}

function toggleDarkLightSpecial(){
    /* Toggle Button Text */
    let current_text = darkl_button.innerText;
    darkl_button.innerText = current_text === 'Dark' ? 'Light' : 'Dark';
    /** Log it */
    console.log("Toggle button clicked!");
    /** Switch background and text colors using css class under wrapper */
    var theme_element = document.body;
    theme_element.classList.toggle("dark-special");
}
