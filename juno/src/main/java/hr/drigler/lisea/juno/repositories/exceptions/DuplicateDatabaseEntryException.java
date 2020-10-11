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
package hr.drigler.lisea.juno.repositories.exceptions;

public class DuplicateDatabaseEntryException extends Exception {

    private static final long serialVersionUID = 8700020198663872447L;

    public DuplicateDatabaseEntryException() {

        super();
        // TODO Auto-generated constructor stub
    }

    public DuplicateDatabaseEntryException(String message, Throwable cause,
        boolean enableSuppression, boolean writableStackTrace) {

        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    public DuplicateDatabaseEntryException(String message, Throwable cause) {

        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    public DuplicateDatabaseEntryException(String message) {

        super(message);
        // TODO Auto-generated constructor stub
    }

    public DuplicateDatabaseEntryException(Throwable cause) {

        super(cause);
        // TODO Auto-generated constructor stub
    }

}
