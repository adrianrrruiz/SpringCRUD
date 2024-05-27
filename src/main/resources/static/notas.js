var porcentaje_acumulado = 0;
var nota_total = 0;
var id_estudiante = 0;

window.onload = function() {
            const params = new URLSearchParams(window.location.search);
            const id = params.get('id');
            id_estudiante = id;
            const nombre = params.get('nombre');
            const apellido = params.get('apellido');
            document.getElementById('titulo').innerHTML = 'Teoría de la computación - Estudiante: ' + decodeURIComponent(nombre) + ' ' + decodeURIComponent(apellido);
            cargaNotas(id_estudiante);
        };


function cargaNotas(id) {
    const xhttp = new XMLHttpRequest();
    xhttp.open("GET", "http://localhost:8081/api/estudiante/" + id);
    xhttp.send();
    xhttp.onreadystatechange = function () {
      if (this.readyState == 4 && this.status == 200) {
        console.log(this.responseText);
        var trHTML = "";
        const object = JSON.parse(this.responseText);
        calcularNota(object["notas"]);
        porcentaje_acumulado = 0;
        for (let nota of object["notas"]){
          trHTML += "<tr>";
          trHTML += "<td>" + nota["id"] + "</td>";
          trHTML += "<td>" + nota["observacion"] + "</td>";
          trHTML += "<td>" + nota["valor"] + "</td>";
          trHTML += "<td>" + nota["porcentaje"] + "</td>";
          trHTML +=
            '<td><button type="button" class="btn btn-outline-secondary" onclick="actualizarNota(' + nota["id"] + ')">Editar</button>';
          trHTML +=
            '<button type="button" class="btn btn-outline-danger" onclick="borrarNota(' +  nota["id"] + ')">Borrar</button></td>';
          trHTML += "</tr>";
          porcentaje_acumulado += nota["porcentaje"];
        }
        document.getElementById("mytable").innerHTML = trHTML;
        actualizarPorcentajeTotal();
      }
    };
  }

function actualizarPorcentajeTotal(){
    document.getElementById('porcentaje_acumulado').innerHTML = 'Con el porcentaje acumulado del  ' + porcentaje_acumulado + '% la nota es: ' +  nota_total.toFixed(2);
}

function calcularNota(notas){
    var total = 0;
    for (let nota of notas){
        total += nota["valor"] * (nota["porcentaje"] / 100);
    }
    nota_total = total;
}

function edicionNota() {
    Swal.fire({
      title: "Crear Nota",
      html:
        '<input id="id" type="hidden">' +
        '<input id="observacion" class="swal2-input"  placeholder="Obsevación">' +
        '<input id="valor" class="swal2-input" placeholder="Valor (Ej. 3.1)">' +
        '<input id="porcentaje" class="swal2-input" placeholder="Porcentaje (Ej. 30.5)">',
      focusConfirm: false,
      preConfirm: () => {
        creaNota();
      },
    });
  }

function creaNota() {
    const observacion = document.getElementById("observacion").value;
    const valor = document.getElementById("valor").value;
    const porcentaje = document.getElementById("porcentaje").value;

    const xhttp = new XMLHttpRequest();
    xhttp.open("POST", "http://localhost:8081/api/nota/crea");
    xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhttp.send(
      JSON.stringify({
        estudiante_id: id_estudiante,
        observacion: observacion,
        valor: valor,
        porcentaje: porcentaje
      })
    );
    xhttp.onreadystatechange = function () {
      if (this.readyState == 4 && this.status == 200) {
        const objects = JSON.parse(this.responseText);
        Swal.fire("La nota se creó correctamente :)");
        cargaNotas(id_estudiante);
      }else if(this.status == 500){
        Swal.fire("El porcentaje total de las notas del estudiante supera el 100%");
      }
    };
  }

  function actualizarNota(id) {
      console.log(id);
      const xhttp = new XMLHttpRequest();
      xhttp.open("GET", "http://localhost:8081/api/nota/" + id );
      xhttp.send();
      xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
          const obj = JSON.parse(this.responseText);
          Swal.fire({
            title: "Editar Nota",
            html:
              '<input id="id" type="hidden" value=' +
              obj.id +
              ">" +
              '<input id="observacion" class="swal2-input" placeholder="Observación" value="' +
              obj.observacion +
              '">' +
              '<input id="valor" class="swal2-input" placeholder="Valor" value="' +
              obj.valor +
              '">' +
              '<input id="porcentaje" class="swal2-input" placeholder="Porcentaje" value="' +
              obj.porcentaje +
              '">',
            focusConfirm: false,
            preConfirm: () => {
              editarNota(obj.id);
            },
          });
        }
      };
    }

    function editarNota(id) {
        const observacion = document.getElementById("observacion").value;
        const valor = document.getElementById("valor").value;
        const porcentaje = document.getElementById("porcentaje").value;

        const xhttp = new XMLHttpRequest();
        xhttp.open("PUT", "http://localhost:8081/api/nota/act/" + id );
        xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
        xhttp.send(
          JSON.stringify({
            id: id,
            observacion: observacion,
            valor: valor,
            porcentaje: porcentaje,
          })
        );
        xhttp.onreadystatechange = function () {
          if (this.readyState == 4 && this.status == 200) {
            const objects = JSON.parse(this.responseText);
            Swal.fire("La nota se actualizó correctamente :)");
            cargaNotas(id_estudiante);
          }else if(this.status == 500){
            Swal.fire("El porcentaje total de las notas del estudiante supera el 100%");
          }
        };
      }

  function borrarNota(id) {
      const xhttp = new XMLHttpRequest();
      xhttp.open("DELETE", "http://localhost:8081/api/nota/borra/" + id );
      xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
      xhttp.send(
        JSON.stringify({
          id: id,
        })
      );
      xhttp.onreadystatechange = function () {
        if (this.status == 204) {
          Swal.fire("Nota Borrado");
          cargaNotas(id_estudiante);
        }
      };
    }