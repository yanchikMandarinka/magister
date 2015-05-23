<div class="panel panel-primary">
    <div class="panel-heading">Network with id=${network.id} and name=${network.name}</div>
    <div class="panel-body">
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>Mote id</th>
                    <th>Mote power</th>
                    <th>Latitude</th>
                    <th>Longtitude</th>
                    <th>Type</th>
                    <th>Delay(seconds)</th>
                    <th>Gateway</th>
                    <th>Broken</th>
                    <th>Alive</th>
                    <th>Actions</th>
                </tr>
            </thead>

            <tbody>
                <c:forEach var="mote" items="${network.motes}">
                    <tr>
                        <td><a href="/mote/show?id=${mote.id}">${mote.id}</a></td>
                        <td>${mote.power}</td>
                        <td>${mote.latitude}</td>
                        <td>${mote.longtitude}</td>
                        <td>${mote.moteType}</td>
                        <td>${mote.delay}</td>
                        <td>${mote.gateway}</td>
                        <td>${mote.broken}</td>
                        <td>${mote.alive}</td>
                        <td>
                        <a href="/mote/burn?id=${mote.id}"><span class="glyphicon glyphicon-fire text-danger" aria-hidden="true"></span></a>
                        <a href="/mote/repair?id=${mote.id}"><span class="glyphicon glyphicon-wrench text-primary" aria-hidden="true"></span></a>
                        <a href="/mote/charge?id=${mote.id}"><span class="glyphicon glyphicon-flash text-warning" aria-hidden="true"></span></a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <!-- a href="/network/create" class="btn btn-success">Create new network</a> -->
    </div>
</div>