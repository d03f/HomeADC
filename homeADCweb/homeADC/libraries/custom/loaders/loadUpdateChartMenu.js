


async function loadUpdateChartMenu() {

    let currContainer = document.currentScript.parentNode;

    let response = await fetch('/libraries/templates/editChart_menu.html');
    let html = await response.text();
    let container = currContainer
    container.innerHTML = html;
  

    let scripts = container.querySelectorAll("script");
    scripts.forEach(script => {
      let newScript = document.createElement("script");
      newScript.text = script.text;
      document.body.appendChild(newScript);
    });
}

loadUpdateChartMenu()