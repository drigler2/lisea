/***************************************************************************
Copyright (C) 2020 Damir Rigler

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; version 2 of the License.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
***************************************************************************/
package hr.drigler.lisea.juno.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.drigler.lisea.juno.repositories.IVulcanRepository;

@Service
public class VulcanService implements IVulcanService {

    IVulcanRepository repo;

    @Autowired
    public VulcanService(IVulcanRepository repo) {

        this.repo = repo;
    }

    @Override
    public Long getVulcanId() {

        return repo.getVulcanId().getVulcanId();
    }

}
